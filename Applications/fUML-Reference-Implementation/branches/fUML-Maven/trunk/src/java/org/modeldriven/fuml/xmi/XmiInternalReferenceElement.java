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

import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.xmi.stream.StreamNode;

/**
 * This class is an XmiReference implementation holding XMI internal reference data 
 * and related information found within XML elements. The character data within a reference 
 * element may hold any number of internal XMI references to entities within a model, 
 * separated by space (' ') character delimiters. Only "internal" references are found in 
 * XML entity character data within an XMI formatted source. Internal references target 
 * model entities found in the same source XMI as the reference. Internal references 
 * within an XML entity may also be found in the xmi:idref attribute of an entity. For 
 * more information on XMI references see the XmiReference interface documentation.
 * 
 * @author Scott Cinnamond
 */
public class XmiInternalReferenceElement extends XmiReferenceElement implements XmiReference {

    private static Log log = LogFactory.getLog(XmiInternalReferenceElement.class);
		
	public XmiInternalReferenceElement(XmiNode node, Classifier classifier) {
		super(node, classifier);	
		construct();
	}
	
	private void construct()
	{
		String data = this.node.getData();
		if (data != null)
		{	
	        StringTokenizer st = new StringTokenizer(data);
            if (!st.hasMoreElements())
                throw new XmiException("one or more id's expected");
            while (st.hasMoreElements())
            {
                String ref = st.nextToken();
                ids.add(ref);
            }
		}
		else
		{
            StreamNode eventNode = (StreamNode)node;                
            QName name = new QName(eventNode.getContext().getXmiNamespace().getNamespaceURI(),
                    XmiConstants.ATTRIBUTE_XMI_IDREF);
            Attribute idref = eventNode.getAttribute(name);
            if (idref != null)
            {
                ids.add(idref.getValue());
            }
            else
                throw new XmiException("expected character data or idref attribute for element, "
                        + eventNode.getLocalName());		    
		}
	}
}
