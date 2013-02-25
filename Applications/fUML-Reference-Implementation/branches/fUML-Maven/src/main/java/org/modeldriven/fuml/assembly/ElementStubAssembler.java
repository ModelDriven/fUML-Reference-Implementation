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
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlObject;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.xmi.ModelSupport;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.stream.StreamNode;

import fUML.Syntax.Classes.Kernel.Comment;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;

public class ElementStubAssembler implements AssemblerResults
{    
    public static String STUB_TOKEN = "#STUB#";
    
    private static Log log = LogFactory.getLog(ElementStubAssembler.class);
    private Repository metadata = Repository.INSTANCE;
    private ElementAssembler result;
    private ModelSupport modelSupport = new ModelSupport();
    private XmiNode target;
    private List<ElementAssemblerEventListener> eventListeners;
    
    @SuppressWarnings("unused")
    private ElementStubAssembler() {}

    public ElementStubAssembler(XmiNode xmiRoot) {
        this.target = xmiRoot;       
    } 
    
    public void start() {
    	assemble();
    }
    
    public void clear() {
        this.result = null;
    }
    
    private void assemble()
    {        
        StreamNode eventNode = (StreamNode)target;
                        
        Classifier classifier = modelSupport.findClassifier(target);
        if (classifier == null)
        {
            classifier = metadata.findClassifier("Activity");
        }
    	if (log.isDebugEnabled())
    		log.debug("identified element '" + target.getLocalName() + "' as classifier, "
    			+ classifier.getName());
    	
        ElementAssembler assembler = new ElementAssembler(target, null,
                (Class_)classifier, null);
        assembler.assembleElementClass();
        
        // FIXME: need some UML model to represent a "stub" element that
        // is intuitive and generic. 
        if (assembler.getTarget() instanceof NamedElement)
        {
            NamedElement namedElement = (NamedElement)assembler.getTarget();
            namedElement.name = "unknown";
            String name = target.getAttributeValue(new QName("name"));
            if (name != null && name.trim().length() > 0)
                namedElement.name = name;
            Comment comment = new Comment();
            comment.body = STUB_TOKEN;
            assembler.getTarget().ownedComment.add(comment);
        }
        else
            throw new AssemblyException("expected instance of NamedElement as target");        
        
        result = assembler;  
        
        if (eventListeners != null)
        	for (ElementAssemblerEventListener listener : eventListeners)
        		listener.elementStubAssembled(
        				new ElementAssemblerResultsEvent(this));
    }
    
    public void addErrorText(String text)
    {
        Comment comment = new Comment();
        comment.body = text;
        result.getTarget().ownedComment.add(comment);
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

    public Element getResult()
    {
        return result.getTarget();
    }
    
    public String getResultId()
    {
        return result.getXmiId();  
    }

	public List<FumlObject> getResults() {
		List<FumlObject> results = new ArrayList<FumlObject>();
		results.add(result.getTarget());
		return results;
	}

	public List<String> getResultsXmiIds() {
		List<String> results = new ArrayList<String>();
		results.add(result.getXmiId());
		return results;
	}

	public Element lookupResult(String xmiId) {
		if (result.getXmiId().equals(xmiId))
			return result.getTarget();
		else
			return null;
	}
}
