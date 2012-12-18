package org.modeldriven.fuml.repository.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.fuml.repository.RepositoryArtifact;



public class Element implements org.modeldriven.fuml.repository.Element {

	protected fUML.Syntax.Classes.Kernel.Element element;
	protected RepositoryArtifact artifact;
    protected List<org.modeldriven.fuml.repository.Extension> elementToExtensionList;
    protected List<org.modeldriven.fuml.repository.Stereotype> elementToStereotypeList;
	
	
    @SuppressWarnings("unused")
	private Element() {}
    
    public Element(fUML.Syntax.Classes.Kernel.Element element, 
    		RepositoryArtifact artifact) {
    	this.element = element;
    	this.artifact = artifact;
    }
    
	public RepositoryArtifact getArtifact() {
		return this.artifact;
	}
    
    public fUML.Syntax.Classes.Kernel.Element getDelegate() {
    	return this.element;
    }

    public String getXmiId() {
    	return this.element.getXmiId();
    }
    
    public String getXmiNamespace() {
    	return this.element.getXmiNamespace();
    }

    public String getHref() {
    	return this.element.getHref();
    }
    
    
    
} // Element
