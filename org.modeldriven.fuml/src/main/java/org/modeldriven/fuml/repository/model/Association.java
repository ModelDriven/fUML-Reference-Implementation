package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class Association extends Classifier 
    implements org.modeldriven.fuml.repository.Association {

	private fuml.syntax.structuredclassifiers.Association association;
	    
    public Association(fuml.syntax.structuredclassifiers.Association association,
    		RepositoryArtifact artifact) {
    	super(association, artifact);
    	this.association = association;
    }
    
	public fuml.syntax.structuredclassifiers.Association getDelegate() {
		return this.association;
	}    
    
} // Association
