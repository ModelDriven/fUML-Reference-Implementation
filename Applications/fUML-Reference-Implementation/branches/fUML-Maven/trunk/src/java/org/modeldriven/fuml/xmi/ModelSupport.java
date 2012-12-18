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

package org.modeldriven.fuml.xmi;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.xmi.stream.StreamNode;


import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.PrimitiveType;

import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.Property;

/**
 * Stateless model-related logic delegate class. Handles metadata related logic specific 
 * for the the XMI package or it's sub-packages.
 * 
 * @author Scott Cinnamond
 */
public class ModelSupport {
    private static Log log = LogFactory.getLog(ModelSupport.class);
		
    public Classifier findClassifier(XmiNode target)
    {
        // First assume the XMI element is not a class name, is an association-end
        // which is the most common case, and we find the type using the 
        // xmi:type attribute which was already set in the target node 
        String name = target.getXmiType();
        if (name == null || name.trim().length() == 0)
        	name = target.getLocalName();
        
        return findClassifierByNamespace(target, name);
    }
    
    public Classifier findClassifier(XmiNode target, Class_ sourceClassifier)
    {
    	Classifier result = null;
    	        
    	// For nodes with an XMI type attrib, don't ignore it
    	// if no classifier found for type. Could result in
    	// abstract class being used based on default
    	// type determination below. 
    	String xmiType = target.getXmiType();
        if (xmiType != null && xmiType.length() > 0) {    
            result = findClassifierByNamespace(target, xmiType);
            
        } else {        
	        Property property = sourceClassifier.findProperty(target.getLocalName());
	        if (property != null) {
	            Classifier type = property.getType();
	            if (type != null) {
	            	String name = type.getName();
	            	result = Repository.INSTANCE.getClassifier(name);
	            	// If the property type is Type or Classifier, and the target is
	            	// not an internal or external reference, then it must be a
	            	// reference to a standard PrimitiveType without an xmi:type
	            	// given.
	            	if ((name.equals("Type") || name.equals("Classifier")) &&
	            			!isInternalReferenceElement((StreamNode)target, result, false) &&
	            			!isExternalReferenceElement((StreamNode)target, result, false)) {
	                    result = Repository.INSTANCE.getClassifier("PrimitiveType");
	            	}
	            }
	        }
        }
        return result;
    }
    
    private Classifier findClassifierByNamespace(XmiNode target, String name) {
        String uri = target.getNamespaceURI();
        if (uri == null || uri.length() == 0)
        	throw new XmiException("no namespace URI found for '"
        			+ name + "'");
        // try the XMI node/element specific namespace
    	if (target.getPrefix() != null)
            uri = target.getNamespaceURI();
        String qualifiedName = uri + "#" + name;
        Classifier result = Repository.INSTANCE.findClassifier(qualifiedName);
        
        // try the default UML URI
        if (result == null) {
        	qualifiedName = Repository.INSTANCE.getDefaultUMLNamespaceURI() + "#" + name;
            result = Repository.INSTANCE.findClassifier(qualifiedName);
        }        
        return result;
    }
    
    public boolean isPrimitiveTypeElement(XmiNode node, Classifier classifier,
			boolean hasAttributes)
	{
		boolean result = false;
    	// if non-reference primitive type property element
    	if (PrimitiveType.class.isAssignableFrom(classifier.getDelegate().getClass()))
    	{
    		if (node.getNodes() != null && node.getNodes().size() > 0)
    			log.warn("found child nodes(s) under primitive type, " 
    					+ classifier.getName());
    		if (hasAttributes)
    			log.warn("found attribute(s) for primitive type, " 
    					+ classifier.getName());
    		result = true; // it's a non-reference property, "can't" have attributes 
    	} 
    	return result;
	}

    /**
     * Returns true if an element is not a primitive type and it has characters 
     * or an ideref XMI attribute. This constitutes an internal reference element.  
     * @param node
     * @param classifier
     * @param hasAttributes
     * @return
     */
    public boolean isInternalReferenceElement(XmiNode node, Classifier classifier,
			boolean hasAttributes)
	{
		boolean result = false;
		
		if (!isPrimitiveTypeElement(node, classifier, hasAttributes))
		{
        	if (node.hasCharacters())
        	{
        		if (hasAttributes)
        			log.warn("found attribute(s) for characters node of type, " 
        					+ classifier.getName());
        		result = true; // characters node    		
        	}
        	else {
                StreamNode eventNode = (StreamNode)node;                
                QName idref = new QName(eventNode.getContext().getXmiNamespace().getNamespaceURI(),
                        XmiConstants.ATTRIBUTE_XMI_IDREF);
                if (node.hasAttribute(idref))
                    return true;
            }
		}
    	return result;
	}

    /**
     * If not a primitive type and we have an href attrib (??). 
     * @param node
     * @param classifier
     * @param hasAttributes
     * @return
     */
    public boolean isExternalReferenceElement(XmiNode node, Classifier classifier,
            boolean hasAttributes)
    {
        // has to be a ref
        boolean result = false;
        
        if (!isPrimitiveTypeElement(node, classifier, hasAttributes))
        {
            QName href = new QName(XmiConstants.ATTRIBUTE_XMI_HREF);
            if (node.hasAttribute(href))
            {
                String hrefValue = node.getAttributeValue(href);
                if (hrefValue == null)
                    return true;
                                
                // FIXME: support Primitive Type references found in an href attribute
                // as external references. 
                int idx = hrefValue.lastIndexOf("#");
                String suffix = hrefValue.substring(idx+1);
                
                if (suffix.equals("Integer") ||
                	suffix.equals("Real") ||
                	suffix.equals("String") ||
                	suffix.equals("Boolean") || 
                	suffix.equals("UnlimitedNatural")) {               	
                
                    return false;
                }
                else {
                    return true;
                }               
            }
        }
        return result;
    }
	
	public boolean isReferenceAttribute(Property property)
	{
		Classifier typeClassifier = property.getType();             
    	if (!PrimitiveType.class.isAssignableFrom(typeClassifier.getDelegate().getClass()) &&
    		!Enumeration.class.isAssignableFrom(typeClassifier.getDelegate().getClass()))
    	{
			return true;
    	}
		return false;
	}}
