/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.assembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlObject;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.xmi.AbstractXmiNodeVisitor;
import org.modeldriven.fuml.xmi.XmiExternalReferenceElement;
import org.modeldriven.fuml.xmi.XmiInternalReferenceElement;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.XmiNodeVisitor;
import org.modeldriven.fuml.xmi.XmiNodeVisitorStatus;
import org.modeldriven.fuml.xmi.stream.StreamNode;
import org.modeldriven.fuml.xmi.validation.ErrorCode;
import org.modeldriven.fuml.xmi.validation.ErrorSeverity;
import org.modeldriven.fuml.xmi.validation.ValidationError;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.PrimitiveType;

public class ElementGraphAssembler extends AbstractXmiNodeVisitor 
    implements XmiNodeVisitor, AssemblerResults {
    private static Log log = LogFactory.getLog(ElementGraphAssembler.class);
    private Repository metadata = Repository.INSTANCE;
    private Map<XmiNode, ElementAssembler> xmiNodeToAssemblerMap = new HashMap<XmiNode, ElementAssembler>();
    private Map<String, ElementAssembler> resultsAssemblerMap = new HashMap<String, ElementAssembler>();
    private List<ElementAssembler> roots = new ArrayList<ElementAssembler>();
    private boolean assembleExternalReferences = true;
    private List<ElementAssemblerEventListener> eventListeners;

    @SuppressWarnings("unused")
    private ElementGraphAssembler() {
    }

    public ElementGraphAssembler(XmiNode xmiRoot, boolean assembleExternalReferences) {
        super(xmiRoot);
        this.assembleExternalReferences = assembleExternalReferences;
    }
    
    public ElementGraphAssembler(XmiNode xmiRoot) {
        this(xmiRoot, true);
    }
    
    public void start() {

        // create an assembler hierarchy
        super.xmiRoot.accept(this);

        for (ElementAssembler root : roots)
            root.acceptBreadthFirst(new ReferenceFeatureAssembler());
        for (ElementAssembler root : roots)
            root.acceptBreadthFirst(new ElementLinker());
        for (ElementAssembler root : roots)
        	root.associateDeferredGeneralizations();
        for (ElementAssembler root : roots)
            root.acceptBreadthFirst(new LibraryRegistration()); // TODO: move to
                                                            // library package      
        for (ElementAssembler root : roots)
            root.acceptBreadthFirst(new NotifyEventListeners());
              
        if (eventListeners != null)
        	for (ElementAssemblerEventListener listener : eventListeners)
        		listener.elementGraphAssembled(
        				new ElementAssemblerResultsEvent(this));            	
    }

    public void clear() {
        this.xmiNodeToAssemblerMap.clear();
        this.classifierMap.clear();
        this.references.clear();
        this.resultsAssemblerMap.clear();
        this.roots.clear();
    }
    
    public void addEventListener(ElementAssemblerEventListener eventListener) {
    	if (eventListeners == null)
    		eventListeners = new ArrayList<ElementAssemblerEventListener>();
    	this.eventListeners.add(eventListener);
    }
    
    public void removeEventListener(ElementAssemblerEventListener eventListener) {
    	if (eventListeners == null)
    		return;
    	this.eventListeners.remove(eventListener);
    }

    public void visit(XmiNode target, XmiNode sourceXmiNode, String sourceKey,
            XmiNodeVisitorStatus status, int level) {
    	
    	// The XMI root is just packaging, not something we want to assemble, so for
    	// models with one or more profiles applied, the XMI root has multiple
    	// child nodes as direct descendants, creating effectively a set of graphs. We ignore
    	// the XMI root here making it's direct descendants actual root assemblers.
    	if (sourceXmiNode == null && "XMI".equals(target.getLocalName())) {
            if (log.isDebugEnabled())
                log.debug("ignoring root XMI node");
    		return;
    	}	
    	
    	XmiNode source = sourceXmiNode;
    	if (source != null && "XMI".equals(source.getLocalName())) {
    		source = null;
            if (log.isDebugEnabled())
                log.debug("ignoring source XMI node as parent");
    	}
    			
        if (log.isDebugEnabled())
            if (source != null)
                log.debug("visit: " + target.getLocalName() + " \t\tsource: "
                        + source.getLocalName());
            else
                log.debug("visit: " + target.getLocalName());

        StreamNode eventNode = (StreamNode) target;

        Classifier classifier = this.findClassifier(target, source);
        if (classifier == null)
        	classifier = findClassifierFromImportAdapter(target);
        if (classifier == null) {
            ValidationError error = new ValidationError(eventNode, ErrorCode.UNDEFINED_CLASS,
                    ErrorSeverity.WARN);
            log.warn(error.toString());
            String xmiType = target.getXmiType();
            if (xmiType != null && xmiType.length() > 0)
                log.warn("ignoring element, " + xmiType);
            else
                log.warn("ignoring element, " + target.getLocalName());
            return;
        }
        if (log.isDebugEnabled())
            log.debug("identified element '" + target.getLocalName() + "' as classifier, "
                    + classifier.getName());
        classifierMap.put(target, classifier);

        // PrimitiveType elements have no attributes or content
        if (classifier.getDelegate() instanceof PrimitiveType) {
        	return;
        }
        
        boolean hasAttributes = eventNode.hasAttributes();
        if (isNotReferenceElement(target, classifier, hasAttributes))
            return; // must be an attribute, handled in ElementAssembler

        ElementAssembler sourceAssembler = null;
        if (source != null)
            sourceAssembler = xmiNodeToAssemblerMap.get(source);

        // If the element is a "child" and represents just a reference, we don't
        // need an assembler
        // for it. Just add it as a reference to the parent for later lookup.
        if (sourceAssembler != null) {
            if (isInternalReferenceElement(eventNode, classifier, hasAttributes)) {
                sourceAssembler.addReference(new XmiInternalReferenceElement(eventNode, classifier));
                return;
            }

            if (isExternalReferenceElement(eventNode, classifier, hasAttributes)) {
                sourceAssembler.addReference(new XmiExternalReferenceElement(eventNode, classifier));
                return;
            }
        }

        ElementAssembler assembler = new ElementAssembler(target, source, 
        		(Class_)classifier, this.resultsAssemblerMap);
        assembler.setAssembleExternalReferences(this.assembleExternalReferences);
        assembler.assembleElementClass();
        assembler.assembleFeatures();
        
        this.resultsAssemblerMap.put(assembler.getXmiId(), assembler);
        this.xmiNodeToAssemblerMap.put(target, assembler);

        // build an assembler hierarchy
        if (sourceAssembler != null) {
            sourceAssembler.add(assembler);
            assembler.setParentAssembler(sourceAssembler);
        }

        if (source == null) {
            roots.add(assembler);
        }
    }

    public List<FumlObject> getResults() {
        List<FumlObject> results = new ArrayList<FumlObject>();
        Iterator<String> keys = resultsAssemblerMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            ElementAssembler assembler = resultsAssemblerMap.get(key);
            results.add(assembler.getTargetObject());
        }
        return results;
    }

    public List<String> getResultsXmiIds() {
        List<String> results = new ArrayList<String>();
        Iterator<String> keys = resultsAssemblerMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            results.add(key);
        }
        return results;
    }

    public FumlObject lookupResult(String xmiId) {
        return resultsAssemblerMap.get(xmiId).getTargetObject();
    }

    class ReferenceFeatureAssembler implements AssemblerVisitor {
        private Log log = LogFactory.getLog(ReferenceFeatureAssembler.class);

        public void begin(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {
            ElementAssembler targetAssembler = (ElementAssembler) target;
            ElementAssembler sourceAssembler = (ElementAssembler) source;

            if (log.isDebugEnabled())
                if (source != null)
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName()
                            + " \t\tsource: " + sourceAssembler.getTargetClass().getSimpleName());
                else
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName());
            if (log.isDebugEnabled())
                log.debug("postassemble: (" + targetAssembler.getTargetClass().getSimpleName()
                        + ")" + " " + targetAssembler.getPrototype().getName());

            targetAssembler.assembleReferenceFeatures();
        }

        public void end(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {

        }

    }

    class ElementLinker implements AssemblerVisitor {
        private Log log = LogFactory.getLog(ElementLinker.class);

        public void begin(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {
            ElementAssembler targetAssembler = (ElementAssembler) target;
            ElementAssembler sourceAssembler = (ElementAssembler) source;
            if (log.isDebugEnabled())
                if (source != null)
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName()
                            + " \t\tsource: " + sourceAssembler.getTargetClass().getSimpleName());
                else
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName());
            if (log.isDebugEnabled())
                log.debug("postassemble: (" + targetAssembler.getTargetClass().getSimpleName()
                        + ")" + " " + targetAssembler.getPrototype().getName());

            if (sourceAssembler != null)
                targetAssembler.associateElement(sourceAssembler);

        }

        public void end(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {

        }
    }

    class LibraryRegistration implements AssemblerVisitor {
        private Log log = LogFactory.getLog(LibraryRegistration.class);

        public void begin(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {
            ElementAssembler targetAssembler = (ElementAssembler) target;
            ElementAssembler sourceAssembler = (ElementAssembler) source;
            if (log.isDebugEnabled())
                if (source != null)
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName()
                            + " \t\tsource: " + sourceAssembler.getTargetClass().getSimpleName());
                else
                    log.debug("begin: " + targetAssembler.getTargetClass().getSimpleName());
            if (log.isDebugEnabled())
                log.debug("postassemble: (" + targetAssembler.getTargetClass().getSimpleName()
                        + ")" + " " + targetAssembler.getPrototype().getName());

            targetAssembler.registerElement();
        }

        public void end(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {

        }
    }
    
    class NotifyEventListeners implements AssemblerVisitor {
        private Log log = LogFactory.getLog(LibraryRegistration.class);

        public void begin(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {
            ElementAssembler targetAssembler = (ElementAssembler) target;
            if (eventListeners != null)
            	for (ElementAssemblerEventListener listener : eventListeners)
            		listener.elementAssembled(
            				new ElementAssemblerEvent(targetAssembler.getTarget()));
        }

        public void end(AssemblerNode target, AssemblerNode source, String sourceKey, int level) {

        }
    }
    

    public boolean isAssembleExternalReferences() {
        return assembleExternalReferences;
    }

    public void setAssembleExternalReferences(boolean assembleExternalReferences) {
        this.assembleExternalReferences = assembleExternalReferences;
    }

}
