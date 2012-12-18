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

import java.util.List;

import javax.xml.namespace.QName;

/**
 * Models the hierarchical aspects of an element within a tree of XMI elements
 * and supports Visitor pattern oriented traversals of the hierarchy.
 * 
 * @author Scott Cinnamond
 */
public interface XmiNode extends XmiElement {

    public List<XmiNode> getNodes();

    public boolean hasNodes();

    public boolean hasCharacters();

    public boolean hasAttribute(QName name);

    public String getAttributeValue(QName name);

    public int getLineNumber();

    public int getColumnNumber();

    public void accept(XmiNodeVisitor visitor);
}
