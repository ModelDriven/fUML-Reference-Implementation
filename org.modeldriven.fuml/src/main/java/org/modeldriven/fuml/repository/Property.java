package org.modeldriven.fuml.repository;

import fuml.syntax.structuredclassifiers.Association;
import fuml.syntax.values.ValueSpecification;


public interface Property extends NamedElement {


    public Class_ getClass_(); 
	
	public ValueSpecification getDefaultValue();

	public void setDefaultValue(ValueSpecification defaultValue);

	public Property getOpposite();
	
	public fuml.syntax.classification.Property getDelegate(); 

    public Classifier getType();
	
    public Classifier findType();
    
    public Association getAssociation(); 
    
    public void setAssociation(Association assoc);
    
    public boolean isDerived();
    
    public boolean isRequired();
    
    public boolean isDataType();

    public boolean isSingular();
    
    public String getPropertyDefault();

    public String findPropertyDefault();

    public boolean hasPropertyDefaultValue();

    public String getUpperValue();

    public int getLowerValue();

} // Property
