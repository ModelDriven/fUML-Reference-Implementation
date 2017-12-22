package org.modeldriven.fuml.repository;



public interface Element {

	public RepositoryArtifact getArtifact();
	
    public fuml.syntax.commonstructure.Element getDelegate();

    public String getXmiId();
    
    public String getXmiNamespace();

    public String getHref();
    
} // Element
