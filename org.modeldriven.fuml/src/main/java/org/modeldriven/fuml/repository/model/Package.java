package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;



public class Package extends NamedElement 
    implements org.modeldriven.fuml.repository.Package 
{

	private fuml.syntax.packages.Package package_;
    private Package nestingPackage;
	    
    public Package(fuml.syntax.packages.Package package_,
    		RepositoryArtifact artifact) {
    	super(package_, artifact);
    	this.package_ = package_;
    }

    public fuml.syntax.commonstructure.PackageableElementList getPackagedElement() {
    	return this.package_.packagedElement;
    }
    
    public fuml.syntax.packages.Package getDelegate() {
    	return this.package_;
    }

    public org.modeldriven.fuml.repository.Package getNestingPackage() {
    	if (nestingPackage == null && this.package_.nestingPackage != null)
    		nestingPackage = new Package(this.package_.nestingPackage, 
    				this.artifact);
    	return this.nestingPackage;
    }
    
} // Package
