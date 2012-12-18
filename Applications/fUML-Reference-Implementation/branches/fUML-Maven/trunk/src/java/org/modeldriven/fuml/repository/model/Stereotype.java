package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;



public class Stereotype extends Class_ 
    implements org.modeldriven.fuml.repository.Stereotype {

	private org.modeldriven.fuml.repository.ext.Stereotype stereotype;
	    
    public Stereotype(org.modeldriven.fuml.repository.ext.Stereotype stereotype,
    		RepositoryArtifact artifact) {
    	super(stereotype, artifact);
    	this.stereotype = stereotype;
    }

} // Stereotype
