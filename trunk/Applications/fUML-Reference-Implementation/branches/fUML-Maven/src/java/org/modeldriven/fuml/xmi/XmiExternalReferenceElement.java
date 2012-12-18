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
import org.modeldriven.fuml.repository.Classifier;

/**
 * This class is an XmiReference implementation holding XMI external reference data 
 * and related information found within XML elements. External references within an XML 
 * entity are found in the xmi:href attribute of an entity.External XMI references target 
 * model entities from an XMI source outside the source where the reference is found. For 
 * more information on XMI references see the XmiReference interface documentation.
 * 
 * @author Scott Cinnamond
 */
public class XmiExternalReferenceElement extends XmiReferenceElement  {

    private static Log log = LogFactory.getLog(XmiExternalReferenceElement.class);	
	
	public XmiExternalReferenceElement(XmiNode node, Classifier classifier) {
		super(node, classifier);	
		construct();
	}
	
	private void construct()
	{
	    QName href = new QName("href");
	    String value = node.getAttributeValue(href);
	    if (value == null || value.trim().length() == 0)
	        throw new XmiException("cannot process external reference for node, " 
	                + node.getLocalName());
	    ids.add(value);
	}
}
