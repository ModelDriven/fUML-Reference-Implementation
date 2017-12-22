package org.modeldriven.fuml.repository.model;

import org.modeldriven.fuml.repository.RepositoryArtifact;


public class DataType extends Classifier 
    implements org.modeldriven.fuml.repository.DataType {

	private fuml.syntax.simpleclassifiers.DataType dataType;
	    
    public DataType(fuml.syntax.simpleclassifiers.DataType dataType,
    		RepositoryArtifact artifact) {
    	super(dataType, artifact);
    	this.dataType = dataType;
    }
    
	public fuml.syntax.simpleclassifiers.DataType getDelegate() {
		return this.dataType;
	}    
    
} // DataType
