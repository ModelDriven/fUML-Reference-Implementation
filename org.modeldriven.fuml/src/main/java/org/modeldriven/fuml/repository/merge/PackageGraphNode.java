package org.modeldriven.fuml.repository.merge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.modeldriven.fuml.repository.Package;

public class PackageGraphNode {
	
    private Log log = LogFactory.getLog(PackageGraphNode.class);
	private String id;
	private Package pkg;
	private List<PackageGraphNode> nodes;
	
	@SuppressWarnings("unused")
	private PackageGraphNode() {}
	public PackageGraphNode(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public Package getPackage() {
		return pkg;
	}
	
	public void setPackage(Package pkg) {
		this.pkg = pkg;
	}
	
	public List<PackageGraphNode> getNodes() {
		return nodes;
	}
	
	public void addNode(PackageGraphNode node) {
		if (nodes == null)
			nodes = new ArrayList<PackageGraphNode>();
		nodes.add(node);
	}
	
    public void accept(PackageGraphVisitor visitor)
    {
        accept(visitor, this, null, new HashMap<PackageGraphNode, PackageGraphNode>());
    }

    private void accept(PackageGraphVisitor visitor, PackageGraphNode target, 
    		PackageGraphNode source, Map<PackageGraphNode, PackageGraphNode> visited)
    {
        if (log.isDebugEnabled())
        	if (source != null)
                log.debug("accept: " + target.getPackage().getQualifiedName() + "<-" + source.getPackage().getQualifiedName());
        	else
                log.debug("accept: " + target.getPackage().getQualifiedName());
        
        if (visited.get(target) == null)
            visited.put(target, target);
        else
        {   
            if (log.isDebugEnabled())
                log.debug("ignoring, " + target.getPackage().getQualifiedName());
            return;
        }           
        
        List<PackageGraphNode> nodes = target.getNodes();
        if (nodes != null)
            for (int i = 0; i < nodes.size(); i++)
            {
            	PackageGraphNode child = nodes.get(i);
                accept(visitor, child, target, visited);                
            }
        
        visitor.visit(target, source);
    }
	public boolean contains(PackageGraphNode source) {
		return nodes != null && nodes.contains(source);
	}
    
}
