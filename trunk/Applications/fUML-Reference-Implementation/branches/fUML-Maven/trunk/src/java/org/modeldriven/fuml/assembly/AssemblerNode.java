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
package org.modeldriven.fuml.assembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.xmi.XmiElement;

public class AssemblerNode implements XmiElement {

    private static Log log = LogFactory.getLog(AssemblerNode.class);
    
    private List<AssemblerNode> assemblers;
    private XmiElement xmiElement; // FIXME: this is redundant!! See ElementAssembler.source

    protected AssemblerNode() {
    }
    
    public AssemblerNode(XmiElement xmiType) {
        this.xmiElement = xmiType;
    }

    public String getXmiType() {
        return xmiElement.getXmiType();
    }
    
    public boolean hasXmiType()
    {
    	String xmiType = xmiElement.getXmiType();
    	return xmiType != null && xmiType.length() > 0;
    }
    
    public String getLocalName() {
        return xmiElement.getLocalName();
    }
    
	public String getNamespaceURI() {
		return xmiElement.getNamespaceURI();
	}

	public String getPrefix() {
		return xmiElement.getPrefix();
	}    
    

    public String getData() {
        return xmiElement.getData();
    }
    
    public String getXmiId() {
        return xmiElement.getXmiId();
    }
    
    public void add(ElementAssembler assembler)
    {
        if (assemblers == null)
            assemblers = new ArrayList<AssemblerNode>();
        assemblers.add(assembler);
    }
    
    public List<AssemblerNode> getNodes() {
        return assemblers;
    }
    
	public void accept(AssemblerVisitor visitor)
    {
        if (log.isDebugEnabled())
            log.debug(this.getClass().getSimpleName());
        acceptBreadthFirst(visitor);
    }
    
    public void acceptDepthFirst(AssemblerVisitor visitor)
    {
        try {
            accept(visitor, this, null, null, this, true, new HashMap<AssemblerNode, AssemblerNode>(), 0);
        }
        finally {    
        }
    }
    
    public void acceptBreadthFirst(AssemblerVisitor visitor)
    {
        try {
            accept(visitor, this, null, null, this, false, new HashMap<AssemblerNode, AssemblerNode>(), 0);
        }
        finally {    
        }
    }
    
    private void accept(AssemblerVisitor visitor, AssemblerNode target, 
            AssemblerNode source, String sourceKey, 
            AssemblerNode root, boolean depthFirst, 
            Map<AssemblerNode, AssemblerNode> visited, int level)
    {
        if (log.isDebugEnabled())
            log.debug("accept: " + target.getLocalName());
        
        if (visited.get(target) == null)
            visited.put(target, target);
        else
        {   
            if (log.isDebugEnabled())
                log.debug("ignoring, " + target.getClass().getName());
            return;
        }    
        
        if (!depthFirst)
            visitor.begin(target, source, sourceKey, level);
        else
            visitor.end(target, source, sourceKey, level);
        
        List<AssemblerNode> nodes = target.getNodes();
        if (nodes != null)
            for (int i = 0; i < nodes.size(); i++)
            {
                AssemblerNode child = nodes.get(i);
                if (log.isDebugEnabled())
                    log.debug("processing node: " + child.getLocalName());
                accept(visitor, child, target, 
                        child.getLocalName(), root, depthFirst, visited, level++);                
            }
        
        if (depthFirst)
            visitor.begin(target, source, sourceKey, level);
        else
            visitor.end(target, source, sourceKey, level);
    }

}
