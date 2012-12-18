package org.modeldriven.fuml.repository.merge;



public interface PackageGraphVisitor {
    public void visit(PackageGraphNode target, PackageGraphNode source);

}
