package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class Association extends Classifier 
    implements org.modeldriven.fuml.repository.Association {

	private fUML.Syntax.Classes.Kernel.Association association;
	    
    public Association(fUML.Syntax.Classes.Kernel.Association association,
    		RepositoryArtifact artifact) {
    	super(association, artifact);
    	this.association = association;
    }
    
	public fUML.Syntax.Classes.Kernel.Association getDelegate() {
		return this.association;
	}    
    
} // Association
