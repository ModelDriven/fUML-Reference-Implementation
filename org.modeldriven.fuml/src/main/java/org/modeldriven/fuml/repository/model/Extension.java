package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class Extension extends Association 
    implements org.modeldriven.fuml.repository.Extension {

	private org.modeldriven.fuml.repository.ext.Extension extension;
	    
    public Extension(org.modeldriven.fuml.repository.ext.Extension extension,
    		RepositoryArtifact artifact) {
    	super(extension, artifact);
    	this.extension = extension;
    }
    
	public org.modeldriven.fuml.repository.ext.Extension getDelegate() {
		return this.extension;
	}    
    
} // Extension
