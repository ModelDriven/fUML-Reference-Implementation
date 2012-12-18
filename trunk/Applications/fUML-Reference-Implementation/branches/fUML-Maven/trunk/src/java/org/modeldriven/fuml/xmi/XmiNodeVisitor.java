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

/**
 * Visitor interface receiving traversal events for XmiNode traversals.
 * 
 * @author Scott Cinnamond
 */
public interface XmiNodeVisitor {

    public void visit(XmiNode target, XmiNode source, String sourceKey,
            XmiNodeVisitorStatus status, int level);
}
