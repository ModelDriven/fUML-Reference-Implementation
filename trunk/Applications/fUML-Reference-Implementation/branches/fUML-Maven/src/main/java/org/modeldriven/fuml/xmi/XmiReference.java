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

import java.util.Iterator;

import org.modeldriven.fuml.repository.Classifier;

/**
 * This interface is used to abstract away differences between the various 
 * types of XMI references allowed within the XMI format. XMI references are 
 * found in both XML attributes as well as XML elements in an XMI source.
 * 
 * XMI ID's and XMI References.
 *  
 * XMI id's are system generated identifiers (typically UUID's) resulting from 
 * the serialization of a model, typically a graph structure, as XML which is a 
 * strict hierarchy. They are found within the (XMI namespace) xmi:id attribute 
 * of many XML elements in a typical XMI formatted file. While the xmi:id attribute 
 * uniquely identifies a single entity within a model, the id may be used to 
 * refer to the entity from any number of other locations within XMI file. XMI 
 * references may be encoded as attributes or elements and may be encoded in several 
 * ways. In addition, multiple references may be found with the same XMI attribute 
 * or element, referring to any number of other entities within the file.  
 * 
 * Internal and External References
 *  
 * Internal XMI references are those that target model entities within the same XMI 
 * source where the reference is found. For instance for an internal reference '123' to 
 * be valid, some element within the same file must have an xmi:id attribute equal 
 * to '123'. External XMI references target model entities from an XMI source outside 
 * the source where the reference is found. External references are encoded differently 
 * than internal references and are only found as XML elements.
 * 
 * @author Scott Cinnamond
 */
public interface XmiReference {
	public String getLocalName();
    public int getLineNumber();
    public int getColumnNumber();
	public int getReferenceCount();
    public Iterator<String> getXmiIds();
    public XmiNode getXmiNode();
    public Classifier getClassifier();
}
