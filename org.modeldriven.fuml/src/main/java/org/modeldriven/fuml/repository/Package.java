package org.modeldriven.fuml.repository;



public interface Package extends NamedElement {

    public fuml.syntax.commonstructure.PackageableElementList getPackagedElement();
    
    public fuml.syntax.packages.Package getDelegate();
    public Package getNestingPackage();
    
} // Package
