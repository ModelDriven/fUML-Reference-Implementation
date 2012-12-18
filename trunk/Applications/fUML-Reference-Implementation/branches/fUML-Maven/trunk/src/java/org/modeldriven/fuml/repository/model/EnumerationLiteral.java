package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class EnumerationLiteral extends InstanceSpecification 
    implements org.modeldriven.fuml.repository.EnumerationLiteral {

	private fUML.Syntax.Classes.Kernel.EnumerationLiteral enumerationLiteral;
	private org.modeldriven.fuml.repository.Enumeration enumeration;
	
    public EnumerationLiteral(fUML.Syntax.Classes.Kernel.EnumerationLiteral enumerationLiteral,
    		RepositoryArtifact artifact) {
    	super(enumerationLiteral, artifact);
    	this.enumerationLiteral = enumerationLiteral;
    }
    
    public org.modeldriven.fuml.repository.Enumeration getEnumeration() {
    	if (enumeration == null)
    		enumeration = new Enumeration(enumerationLiteral.enumeration, 
    			this.artifact);
    	return enumeration;
    }

} // EnumerationLiteral
