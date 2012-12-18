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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.stream.events.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.Classifier;

/**
 * This class is an XmiReference implementation holding reference data and 
 * related information found within XML attributes. An attribute may hold any 
 * number of references to entities within a mode, separated by space (' ') character 
 * delimiters. Only "internal" references are found in XML attributes within an XMI 
 * formatted source. Internal references target model entities found in the same source 
 * XMI as the reference. For more information on XMI references, see the XmiReference 
 * interface documentation.
 * 
 * @author Scott Cinnamond
 */
public class XmiReferenceAttribute implements XmiReference {

    private static Log log = LogFactory.getLog(XmiReferenceElement.class);
	private XmiNode node;
	private Attribute attribute;
	private List<String> ids = new ArrayList<String>();
	protected Classifier classifier;
	
	@SuppressWarnings("unused")
	private XmiReferenceAttribute() {}
	
	public XmiReferenceAttribute(XmiNode node, Attribute attribute, Classifier classifier) {
		this.node = node;	
		this.attribute = attribute;
        this.classifier = classifier;
		construct();
	}
	
	public Classifier getClassifier() {
	    return this.classifier;	
	}
	
	public XmiNode getXmiNode() {
		return this.node;
	}
	
	private void construct()
	{
		String data = this.attribute.getValue();
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
			log.warn("expected value for attribute, " 
					+ this.attribute.getName().getLocalPart());
	}

	public String getLocalName() {
		return this.attribute.getName().getLocalPart();
	}
	
    public int getLineNumber() {
        return node.getLineNumber();	
    }
    
    public int getColumnNumber() {
        return node.getColumnNumber();	
    }

	public int getReferenceCount() {
		return ids.size();
	}
	
    public Iterator<String> getXmiIds() {
    	return ids.iterator();
    }
	
}
