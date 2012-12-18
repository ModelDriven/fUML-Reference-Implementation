package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.config.Artifact;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.RepositoryMapping;

public abstract class ModelAssembler {
    protected Artifact artifact;
    protected RepositoryMapping mapping;
    protected Repository model;
   
    protected ModelAssembler(Artifact artifact, RepositoryMapping mapping, Repository model) {
	    this.artifact = artifact;
	    this.mapping = mapping;
	    this.model = model;
    }
}
