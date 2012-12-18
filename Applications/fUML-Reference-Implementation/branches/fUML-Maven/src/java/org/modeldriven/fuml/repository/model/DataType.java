package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class DataType extends Classifier 
    implements org.modeldriven.fuml.repository.DataType {

	private fUML.Syntax.Classes.Kernel.DataType dataType;
	    
    public DataType(fUML.Syntax.Classes.Kernel.DataType dataType,
    		RepositoryArtifact artifact) {
    	super(dataType, artifact);
    	this.dataType = dataType;
    }
    
	public fUML.Syntax.Classes.Kernel.DataType getDelegate() {
		return this.dataType;
	}    
    
} // DataType
