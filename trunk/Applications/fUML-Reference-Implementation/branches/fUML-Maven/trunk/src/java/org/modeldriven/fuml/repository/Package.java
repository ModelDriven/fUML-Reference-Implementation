package org.modeldriven.fuml.repository;



public interface Package extends NamedElement {

    public fUML.Syntax.Classes.Kernel.PackageableElementList getPackagedElement();
    
    public fUML.Syntax.Classes.Kernel.Package getDelegate();
    public Package getNestingPackage();
    
} // Package
