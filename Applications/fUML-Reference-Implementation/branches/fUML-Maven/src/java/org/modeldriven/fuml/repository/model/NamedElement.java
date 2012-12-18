package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class NamedElement extends Element 
    implements org.modeldriven.fuml.repository.NamedElement {

	private fUML.Syntax.Classes.Kernel.NamedElement namedElement;
	
    public NamedElement(fUML.Syntax.Classes.Kernel.NamedElement namedElement,
    		RepositoryArtifact artifact) {
    	super(namedElement, artifact);
    	this.namedElement = namedElement;
    }
    
    public String getQualifiedName() {
    	return this.namedElement.qualifiedName;
    }
    
    public String getName() {
    	return this.namedElement.name;
    }

} // NamedElement
