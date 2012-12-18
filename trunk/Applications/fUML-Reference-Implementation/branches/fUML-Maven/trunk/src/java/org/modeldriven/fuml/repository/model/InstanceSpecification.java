package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class InstanceSpecification extends NamedElement 
    implements org.modeldriven.fuml.repository.InstanceSpecification {

	private fUML.Syntax.Classes.Kernel.InstanceSpecification instanceSpecification;
	
    public InstanceSpecification(fUML.Syntax.Classes.Kernel.InstanceSpecification instanceSpecification,
    		RepositoryArtifact artifact) {
    	super(instanceSpecification, artifact);
    	this.instanceSpecification = instanceSpecification;
    }


} // InstanceSpecification
