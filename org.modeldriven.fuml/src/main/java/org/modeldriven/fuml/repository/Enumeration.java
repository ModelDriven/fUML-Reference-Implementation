package org.modeldriven.fuml.repository;

import java.util.List;


public interface Enumeration extends DataType {

	public fuml.syntax.simpleclassifiers.Enumeration getDelegate();  
	
	public List<EnumerationLiteral> getOwnedLiteral();
    
} // Enumeration
