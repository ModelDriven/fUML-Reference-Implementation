package org.modeldriven.fuml.repository;

import java.util.List;

import org.modeldriven.fuml.repository.model.OpaqueBehavior;


public interface Class_ extends Classifier {

	public fUML.Syntax.Classes.Kernel.Class_ getDelegate(); 
	    
	public Property getProperty(String name);
	public Property findProperty(String name);
    public List<Property> getNamedProperties();
    public List<Property> getAllProperties();
    public List<Property> getDeclaredProperties();
    public List<OpaqueBehavior> getOpaqueBehaviors();
    
    
} // Class_
