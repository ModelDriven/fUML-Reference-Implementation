package org.modeldriven.fuml.repository;



public interface Element {

	public RepositoryArtifact getArtifact();
	
    public fUML.Syntax.Classes.Kernel.Element getDelegate();

    public String getXmiId();
    
    public String getXmiNamespace();

    public String getHref();
    
} // Element
