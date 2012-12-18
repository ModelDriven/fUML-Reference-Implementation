/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.xmi.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.config.ImportAdapter;
import org.modeldriven.fuml.config.NamespaceDomain;
import org.modeldriven.fuml.config.ReferenceMappingType;
import org.modeldriven.fuml.config.ValidationExemption;
import org.modeldriven.fuml.config.ValidationExemptionType;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.library.Library;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.Stereotype;
import org.modeldriven.fuml.xmi.AbstractXmiNodeVisitor;
import org.modeldriven.fuml.xmi.ModelSupport;
import org.modeldriven.fuml.xmi.XmiExternalReferenceElement;
import org.modeldriven.fuml.xmi.XmiInternalReferenceElement;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.XmiNodeVisitor;
import org.modeldriven.fuml.xmi.XmiNodeVisitorStatus;
import org.modeldriven.fuml.xmi.XmiReference;
import org.modeldriven.fuml.xmi.XmiReferenceAttribute;
import org.modeldriven.fuml.xmi.stream.StreamNode;

/**
 * A visitor pattern implementation class that walks a given XmiNode hierarchy checking for
 * various error conditions, creating and gathering ValidationError instances into a collection
 * helpful for user feedback. This class can be initialized with an initial list of errors gathered
 * from another context, and errors may be added to it's collection ad-hoc. This class heavily
 * leverages the fuml.model package. For more information on runtime metadata, see the fuml.model
 * package documentation.
 *
 * @author Scott Cinnamond
 */
public class ValidationErrorCollector extends AbstractXmiNodeVisitor
    implements XmiNodeVisitor
{
    private static Log log = LogFactory.getLog(ValidationErrorCollector.class);
	private List<ValidationError> errors = new ArrayList<ValidationError>();
	private boolean validateExternalReferences = true;
    private List<ValidationEventListener> eventListeners;

	@SuppressWarnings("unused")
	private ValidationErrorCollector() {
	}

	public ValidationErrorCollector(XmiNode root) {
		this.xmiRoot = root;
		modelSupport = new ModelSupport();
	}

	/**
	 * Traverses the entire given graph ala. the visitor pattern, gathering any validation
	 * errors, other than reference errors. References are added to
	 * a collection to be validated subsequently, outside of the
	 * visitor.
	 */
	public void validate()
	{
        if (eventListeners != null)
        	for (ValidationEventListener listener : eventListeners)
        		listener.validationStarted(
        				new ValidationEvent(this));
		
        this.xmiRoot.accept(this);
		this.validateReferences();
		
        if (eventListeners != null)
        	for (ValidationEventListener listener : eventListeners)
        		listener.validationCompleted(
        				new ValidationEvent(this));
	}

    public void addEventListener(ValidationEventListener eventListener) {
    	if (eventListeners == null)
    		eventListeners = new ArrayList<ValidationEventListener>();
    	this.eventListeners.add(eventListener);
    }
    
    public void removeEventListener(ValidationEventListener eventListener) {
    	if (eventListeners == null)
    		return;
    	this.eventListeners.remove(eventListener);
    }

	public void visit(XmiNode target, XmiNode sourceXmiNode, String sourceKey,
	        XmiNodeVisitorStatus status, int level)
	{
		
    	// The XMI root is just packaging, not something we want to assemble or validate.
    	if (sourceXmiNode == null && "XMI".equals(target.getLocalName())) {
            if (log.isDebugEnabled())
                log.debug("ignoring root XMI node");
    		return;
    	}	
    	
    	XmiNode source = sourceXmiNode;
    	if (source != null && "XMI".equals(source.getLocalName())) {
    		source = null;
            if (log.isDebugEnabled())
                log.debug("ignoring source XMI node");
    	}
		
        if (log.isDebugEnabled())
            if (source != null)
                log.debug("visit: " + target.getLocalName()
                    + " \t\tsource: " + source.getLocalName());
            else
                log.debug("visit: " + target.getLocalName());

        StreamNode eventNode = (StreamNode)target;

        if (target.getXmiId() != null)
        {
            if (this.nodeMap.get(target.getXmiId()) == null) {
        	    this.nodeMap.put(target.getXmiId(), target);
            }
            else
                addError(ErrorCode.DUPLICATE_REFERENCE,
                        ErrorSeverity.FATAL, eventNode);
        }

		Classifier classifier = findClassifier(target, source);
		if (classifier == null)
		{
			// See if this element or it's immediate parent has an import adapter
			// which means adapted elements can currently have only 2 levels otherwise
			// the results are undefined. Bad. 
            ImportAdapter importAdapter = FumlConfiguration.getInstance().findImportAdapter(
            		target.getLocalName());
            if (importAdapter == null)
            	importAdapter = FumlConfiguration.getInstance().findImportAdapter(
                		target.getXmiType()); 
            if (source != null) {
	            if (importAdapter == null)
	            	importAdapter = FumlConfiguration.getInstance().findImportAdapter(
	                		source.getLocalName()); 
	            if (importAdapter == null)
	            	importAdapter = FumlConfiguration.getInstance().findImportAdapter(
	                		source.getXmiType());
            }
            
            // if exists, assume the adapter will transform the 
            // undefined element and any descendants
            if (importAdapter == null)
                addError(ErrorCode.UNDEFINED_CLASS, ErrorSeverity.FATAL, eventNode);
            
		    return; // We're done w/this node. No way to examine further without a classifier.
		}

    	if (log.isDebugEnabled())
    		log.debug("identified element '" + target.getLocalName() + "' as classifier, "
    			+ classifier.getName());
    	classifierMap.put(target, classifier);
    	
        boolean hasAttributes = eventNode.hasAttributes();
    	if (isPrimitiveTypeElement(eventNode, classifier, hasAttributes))
    		return;

    	if (isInternalReferenceElement(eventNode, classifier, hasAttributes))
    	{
    		references.add(new XmiInternalReferenceElement(eventNode, classifier));
        	return;
    	}

        if (isExternalReferenceElement(eventNode, classifier, hasAttributes))
        {
            references.add(new XmiExternalReferenceElement(eventNode, classifier));
            return;
        }
        
    	if (isAbstract(classifier))
    	{
            addError(ErrorCode.ABSTRACT_CLASS_INSTANTIATION,
                    ErrorSeverity.FATAL, eventNode);
        	return;
    	}

    	// validate target node as attribute of source
    	if (source != null) {
    		Class_ sourceClassifier = (Class_)classifierMap.get(source);
    	    if (sourceClassifier != null) {
	            Property property = sourceClassifier.findProperty(target.getLocalName());
	            if (property == null) {
	                NamespaceDomain domain = FumlConfiguration.getInstance().findNamespaceDomain(target.getNamespaceURI());	            		               
	            	ValidationExemption exemption = null;
	            	if (domain != null)
	            		exemption = FumlConfiguration.getInstance().findValidationExemptionByProperty(ValidationExemptionType.UNDEFINED_PROPERTY,
	            			sourceClassifier, target.getLocalName(), target.getNamespaceURI(), domain);
	            	if (exemption == null) {
	                    addError(ErrorCode.UNDEFINED_PROPERTY,
	                            ErrorSeverity.FATAL, (StreamNode)source, target.getLocalName());
	            	}
	            	else {
	    				if (log.isDebugEnabled())
	    					log.debug("undefined property exemption found within domain '" 
	    						+ exemption.getDomain().toString() + "' for property '"
	    						+ sourceClassifier.getName() + "." +target.getLocalName() + "' - ignoring error");
	            		
	            	}
	            }
    	    } 
    	    // else source is an undefined class, and we handled that previously
    	}
    	
    	if (eventNode.hasAttributes())
    	    validateAttributes(eventNode, source, (Class_)classifier, eventNode.getAttributes());

    	validateAttributesAgainstModel(eventNode, source, (Class_)classifier);
	}

	private void validateAttributes(StreamNode target, XmiNode source,
			Class_ classifier,
			Iterator<Attribute> attributes)
	{
        NamespaceDomain domain = null; // only lookup as needed
        
        // look at XML attributes
        while (attributes.hasNext())
        {
            Attribute xmlAttrib = attributes.next();

            QName name = xmlAttrib.getName();
            String prefix = name.getPrefix();
            if (prefix != null && prefix.length() > 0)
                continue; // not applicable to current element/association-end.
            if ("href".equals(name.getLocalPart()))
                continue; // FIXME: why is this "special" ?
            
            Property property = classifier.findProperty(name.getLocalPart());
            if (property == null)
            {
            	if (domain == null)
            		domain = FumlConfiguration.getInstance().findNamespaceDomain(target.getNamespaceURI());	            	

            	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByProperty(ValidationExemptionType.UNDEFINED_PROPERTY,
            			classifier, name.getLocalPart(), target.getNamespaceURI(), domain);
            	if (exemption == null) {
                    addError(ErrorCode.UNDEFINED_PROPERTY,
                            ErrorSeverity.FATAL, target, name.getLocalPart());
            	}
            	else {
    				if (log.isDebugEnabled())
    					log.debug("undefined property exemption found within domain '" 
    						+ exemption.getDomain().toString() + "' for property '"
    						+ classifier.getName() + "." +name.getLocalPart() + "' - ignoring error");
            		
            	}
                continue;
            }

            if (isReferenceAttribute(property))
            {
    		    XmiReferenceAttribute reference = new XmiReferenceAttribute(target,
        				xmlAttrib, classifier);
        		this.references.add(reference);
            }
            // TODO: when this error is commented out, an erroneous 'invalid internal reference' validation
            // error was seen to be thrown during assembly. This seems to be a bug
        	if (property.isDerived())
                if (checkDerivedPropertyInstantiationError(target, source, classifier, property)) {
                    addError(ErrorCode.DERIVED_PROPERTY_INSTANTIATION,
                            ErrorSeverity.FATAL, target, property.getName());
                }

        }
	}

	private boolean checkDerivedPropertyInstantiationError(StreamNode target, XmiNode source,
	        Classifier classifier, Property property)
	{
		boolean error = false;

    	String value = target.getAttributeValue(property.getName());
        if (value != null && value.trim().length() > 0)
        {
        	error = true;
        }
        else if (target.findChildByName(property.getName()) != null)
        	error = true;

		return error;
	}

	private void validateAttributesAgainstModel(StreamNode target, XmiNode source,
	        Class_ classifier)
	{
        NamespaceDomain domain = null; // only lookup as needed	            	
        List<Property> properties = classifier.getNamedProperties();
        for (Property prop : properties) {
        	if (prop.isRequired()) {
	            if (checkRequiredPropertyError(target, source, classifier, prop)) {
	            	if (domain == null)
	            		domain = FumlConfiguration.getInstance().findNamespaceDomain(target.getNamespaceURI());	
	            	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByProperty(ValidationExemptionType.REQUIRED_PROPERTY,
	            			classifier, prop.getName(), target.getNamespaceURI(), domain);
	            	if (exemption == null) {
	                    addError(ErrorCode.PROPERTY_REQUIRED,
	                        ErrorSeverity.FATAL, target, prop.getName());
	            	}
	            	else {
	    				if (log.isDebugEnabled())
	    					log.debug("required property exemption found within domain '" 
	    						+ exemption.getDomain().toString() + "' for property '"
	    						+ classifier.getName() + "." + prop.getName() + "' - ignoring error");
	            		
	            	}
	            }
	        }
        	// other checks??
        }
	}
	
	private boolean checkRequiredPropertyError(StreamNode target, XmiNode source,
	        Classifier classifier, Property property)
	{
		boolean error = true;

    	if (property.isDerived())
    	{
    		error = false;
    		return error;
    	}

    	String value = target.getAttributeValue(property.getName());
        if (value != null && value.trim().length() > 0)
        {
        	error = false;
        }
        else // or we have a default
        {
            String defaultValue = property.findPropertyDefault();
            if (defaultValue != null)
            	error = false;
        }

        if (target.findChildByName(property.getName()) != null)
        	error = false;

        // check configured mappings
        if (error && FumlConfiguration.getInstance().hasReferenceMapping(
                classifier, property))
        {
            ReferenceMappingType mappingType =
                FumlConfiguration.getInstance().getReferenceMappingType(
                        classifier, property);
            if (mappingType == ReferenceMappingType.PARENT)
            {
                // All right if the value of this reference property can be derived
                // from it's "parent", does the parent have an XMI id to
                // supply the value??
                if (source.getXmiId() != null && source.getXmiId().length() > 0)
                	error = false;
                else
                    log.warn("no parent XMI id found for, "
                        + classifier.getName() + "." + property.getName());
            }
            else
                log.warn("unrecognized mapping type, "
                        + mappingType.value() + " ignoring mapping for, "
                        + classifier.getName() + "." + property.getName());
        }

        return error;

	}

	private void validateReferences()
	{
        NamespaceDomain domain = null; // only lookup as needed

        // validate references post traversal
        Iterator<XmiReference> iter =  references.iterator();
        while (iter.hasNext())
        {
            XmiReference reference = iter.next();
            Iterator<String> refIter = reference.getXmiIds();
            while (refIter.hasNext())
            {
                String id = refIter.next();

                if (reference instanceof XmiExternalReferenceElement)
                {
                    if (validateExternalReferences)
                    {
                        if (Library.getInstance().lookup(id) != null) {
                            // happy
                        } else if (id != null && id.startsWith("pathmap:")) {
                        	// FIXME: resolve these references inside/outside of lib(s)
                        } else {
                            StreamNode streamNode = (StreamNode)reference.getXmiNode();
                        	if (domain == null)
                        		domain = FumlConfiguration.getInstance().findNamespaceDomain(streamNode.getNamespaceURI());	            	

                        	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByReference(ValidationExemptionType.EXTERNAL_REFERENCE,
                        			reference.getClassifier(), id, streamNode.getNamespaceURI(), domain);
                        	if (exemption == null) {
                                addError(ErrorCode.INVALID_EXTERNAL_REFERENCE, ErrorSeverity.FATAL, 
                                		reference, id);
                        	}                	
                        }
                    }
                }
                else // internal reference
                {
                    if (nodeMap.get(id) != null)
                        continue; // it's an internal reference relative to this validation frame

                    if (Environment.getInstance().findElementById(id) != null)
                        continue; // it's an already loaded internal reference

                    addError(ErrorCode.INVALID_REFERENCE, ErrorSeverity.FATAL, 
                    		reference, id);
                }

            }
        }
	}

	private void addError(ErrorCode code, ErrorSeverity severity, StreamNode eventNode)
	{
        if (log.isDebugEnabled())
            log.debug("adding " + code.toString()
                + " error for element '" + eventNode.getLocalName() + "'");
        
        ValidationError error = new ValidationError(eventNode, code, severity);
        errors.add(error);
        if (eventListeners != null)
        	for (ValidationEventListener listener : eventListeners)
        		listener.validationError(
        				new ValidationErrorEvent(error, this));
	}

    private void addError(ErrorCode code, ErrorSeverity severity,
            StreamNode eventNode, String name)
    {
        if (log.isDebugEnabled())
            log.debug("adding " + code.toString()
                + " error for element '" + eventNode.getLocalName() + "'");
        ValidationError error = new ValidationError(eventNode, name, code, severity);
        errors.add(error);
        if (eventListeners != null)
        	for (ValidationEventListener listener : eventListeners)
        		listener.validationError(
        				new ValidationErrorEvent(error, this));
    }
    
    private void addError(ErrorCode code, ErrorSeverity severity,
    		XmiReference reference, String id) 
    {
        if (log.isDebugEnabled())
            log.debug("adding " + code.toString()
                + " error for element '" + id + "'");
    	ValidationError error = new ValidationError(reference, id,
    			code, severity);
        errors.add(error);
        if (eventListeners != null)
        	for (ValidationEventListener listener : eventListeners)
        		listener.validationError(
        				new ValidationErrorEvent(error, this));
    }

	public List<ValidationError> getErrors() {
		return errors;
	}

    public int getErrorCount() {
        return errors.size();
    }

    public List<ValidationError> getErrors(ErrorCode code) {
        List<ValidationError> results = new ArrayList<ValidationError>();
        Iterator<ValidationError> iter = errors.iterator();
        while (iter.hasNext())
        {
            ValidationError error = iter.next();
            if (error.getCode().ordinal() == code.ordinal())
                results.add(error);
        }
        return results;
    }

    public int getErrorCount(ErrorCode code) {
        return getErrors(code).size();
    }

    public List<ValidationError> getErrors(ErrorCategory category) {
        List<ValidationError> results = new ArrayList<ValidationError>();
        Iterator<ValidationError> iter = errors.iterator();
        while (iter.hasNext())
        {
            ValidationError error = iter.next();
            if (error.getCategory().ordinal() == category.ordinal())
                results.add(error);
        }
        return results;
    }

    public int getErrorCount(ErrorCategory category) {
        return getErrors(category).size();
    }

    public List<ValidationError> getErrors(ErrorSeverity severity) {
        List<ValidationError> results = new ArrayList<ValidationError>();
        Iterator<ValidationError> iter = errors.iterator();
        while (iter.hasNext())
        {
            ValidationError error = iter.next();
            if (error.getSeverity().ordinal() == severity.ordinal())
                results.add(error);
        }
        return results;
    }

    public int getErrorCount(ErrorSeverity severity) {
        return getErrors(severity).size();
    }

    public boolean isValidateExternalReferences() {
        return validateExternalReferences;
    }

    public void setValidateExternalReferences(boolean validateExternalReferences) {
        this.validateExternalReferences = validateExternalReferences;
    }


}
