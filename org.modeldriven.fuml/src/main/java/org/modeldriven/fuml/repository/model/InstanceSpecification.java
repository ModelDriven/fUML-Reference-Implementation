package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class InstanceSpecification extends NamedElement 
    implements org.modeldriven.fuml.repository.InstanceSpecification {

	private fuml.syntax.classification.InstanceSpecification instanceSpecification;
	
    public InstanceSpecification(fuml.syntax.classification.InstanceSpecification instanceSpecification,
    		RepositoryArtifact artifact) {
    	super(instanceSpecification, artifact);
    	this.instanceSpecification = instanceSpecification;
    }


} // InstanceSpecification
