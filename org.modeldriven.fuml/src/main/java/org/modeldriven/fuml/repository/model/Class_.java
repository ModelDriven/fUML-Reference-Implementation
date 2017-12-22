package org.modeldriven.fuml.repository.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.modeldriven.fuml.repository.NameCollisionException;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.xmi.XmiException;

import fuml.syntax.classification.Operation;
import fuml.syntax.commonbehavior.Behavior;



public class Class_ extends Classifier 
    implements org.modeldriven.fuml.repository.Class_ {

	private fuml.syntax.structuredclassifiers.Class_ class_;
	private List<Property> attributes;
	private Map<String, Property> namedAttributes;
	private List<Property> declaredAttributes;
	private List<Operation> operations;
	    
    public Class_(fuml.syntax.structuredclassifiers.Class_ class_,
    		RepositoryArtifact artifact) {
    	super(class_, artifact);
    	this.class_ = class_;
    }
    
    // note: package-level access only
    void setAttributes(List<Property> attributes) { 
    	    	 
    	this.attributes = attributes;
    	
    	if (this.attributes != null)
    	    for (Property prop : this.attributes)
    	    	if (prop.getName() != null && prop.getName().trim().length() != 0)
    	    	{
                    if (this.namedAttributes == null)
                    	this.namedAttributes = new HashMap<String, Property>();
                    //Property existing = this.namedAttributes.get(prop.getName());
                    //if (existing != null)
                    //	throw new NameCollisionException("the class '" 
                    //		+ this.getQualifiedName() 
                    //		+ "' contains multiple properties named '"
                    //		+ prop.getName() + "' - the named properties for each class "
                    //		+ "and it's generalizations (ancestry) must be unique");
                    this.namedAttributes.put(prop.getName(), prop);
    	    	}
    }

    // note: package-level access only
    void setOperations(List<Operation> operations) {
    	this.operations = operations;
    }
    
    public fuml.syntax.classification.PropertyList getOwnedAttribute() {
    	return this.class_.ownedAttribute;
    }
    
    public fuml.syntax.classification.OperationList getOwnedOperation() {
    	return this.class_.ownedOperation;
    }
    
	public fuml.syntax.structuredclassifiers.Class_ getDelegate() {
		return this.class_;
	}
 
    public Property getProperty(String name) {
        return getProperty(name, false);
    }

    public Property findProperty(String name) {
        return getProperty(name, true);
    }

    private Property getProperty(String name, boolean supressErrors) {
        Property result = null;
        if (this.namedAttributes != null)
        	result = this.namedAttributes.get(name);
        if (result == null && !supressErrors)
            throw new XmiException("no attribute found for, " + this.getName() + "." + name);
        return result;
    }
    
    public List<Property> getNamedProperties() {
    	List<Property> result = new ArrayList<Property>();
        if (namedAttributes != null) {
            int i = 0;
    		for (Iterator<Property> it = namedAttributes.values().iterator(); it.hasNext();) {
    			result.add(it.next());
    			i++;
    		}	
        }
        return result;
    }
    
    public List<Property> getAllProperties() {
        if (attributes != null)
    	    return attributes;
        else
        	return new ArrayList<Property>();
    }

    public List<Property> getDeclaredProperties() {
        if (declaredAttributes == null) {
        	declaredAttributes = new ArrayList<Property>(class_.ownedAttribute.size());
        	
        	for (fuml.syntax.classification.Property p : class_.ownedAttribute)
        	{
        		Property property = new org.modeldriven.fuml.repository.model.Property(p, 
        				this.getArtifact());
        		declaredAttributes.add(property);
        	}
        }
    	return declaredAttributes;
    }
    
    public List<OpaqueBehavior> getOpaqueBehaviors()
    {
    	List<OpaqueBehavior> result = new ArrayList<OpaqueBehavior>(
    			this.getDelegate().ownedBehavior.size());
    	for (Behavior behavior : this.getDelegate().ownedBehavior) {
    		if (behavior instanceof fuml.syntax.commonbehavior.OpaqueBehavior)
    		result.add(new OpaqueBehavior((fuml.syntax.commonbehavior.OpaqueBehavior)behavior, 
    				this.artifact));
    	}
    	return result;
    }
    
} // Class_
