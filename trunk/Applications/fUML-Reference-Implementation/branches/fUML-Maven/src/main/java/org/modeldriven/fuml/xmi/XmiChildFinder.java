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
 * Class that leverages the Visitor pattern to locate descendant node(s) 
 * within an XmiNode hierarchy.
 * 
 * @author Scott Cinnamond
 */
public class XmiChildFinder implements XmiNodeVisitor {
   
    private String localName;
    private XmiNode result;
    
    @SuppressWarnings("unused")
    private XmiChildFinder() {}
    
    public XmiChildFinder(String localName) {
        this.localName = localName;
    }    
    
    public void visit(XmiNode target, XmiNode source, 
            String sourceKey, XmiNodeVisitorStatus status , int level)
    {
        if (source == null)
            return; // skip the root, we want children only
                
        if (target.getLocalName().equals(localName))
        {    
            result = target;
            status.setStatus(XmiNodeVisitorStatus.STATUS_ABORT);
        }
    }

    public XmiNode getResult() {
        return result;
    }   
    
    
    
    
}
