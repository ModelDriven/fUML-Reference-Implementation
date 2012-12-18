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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.Classifier;

/**
 * This class is an XmiReference implementation holding derived XMI internal
 * reference data and related information. Internal reference are derived based
 * configuration mapping information and used to create a mapped-reference. For
 * more information on XMI references see the XmiReference interface
 * documentation.
 * 
 * @author Scott Cinnamond
 */
// FIXME: can this class go away if we also link using property opposites?
public class XmiMappedReference implements XmiReference {

    private static Log log = LogFactory.getLog(XmiMappedReference.class);
    private XmiNode node;
    private String name;
    private List<String> ids = new ArrayList<String>();
	protected Classifier classifier;

    @SuppressWarnings("unused")
    private XmiMappedReference() {
    }

    public XmiMappedReference(XmiNode node, String name, String[] values,
    		Classifier classifier) {
        this.node = node;
        this.name = name;
        this.classifier = classifier;
        construct(values);
    }

	public Classifier getClassifier() {
	    return this.classifier;	
	}
	
	private void construct(String[] values) {
        for (int i = 0; i < values.length; i++)
            ids.add(values[i]);
    }

    public String getLocalName() {
        return name;
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

	public XmiNode getXmiNode() {
		return this.node;
	}

}
