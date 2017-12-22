package org.modeldriven.fuml.repository.model;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.EnumerationLiteral;
import org.modeldriven.fuml.repository.RepositoryArtifact;


public class Enumeration extends DataType 
    implements org.modeldriven.fuml.repository.Enumeration {

	private fuml.syntax.simpleclassifiers.Enumeration enumeration;
	    
    public Enumeration(fuml.syntax.simpleclassifiers.Enumeration enumeration,
    		RepositoryArtifact artifact) {
    	super(enumeration, artifact);
    	this.enumeration = enumeration;
    }
    
	public fuml.syntax.simpleclassifiers.Enumeration getDelegate() {
		return this.enumeration;
	}

	public List<EnumerationLiteral> getOwnedLiteral() {
		
		List<EnumerationLiteral> result = new ArrayList<EnumerationLiteral>();
		
		for (fuml.syntax.simpleclassifiers.EnumerationLiteral literal : this.enumeration.ownedLiteral) {
			result.add(new org.modeldriven.fuml.repository.model.EnumerationLiteral(literal, this.getArtifact()));
		}
		return result;
	}       
} 
