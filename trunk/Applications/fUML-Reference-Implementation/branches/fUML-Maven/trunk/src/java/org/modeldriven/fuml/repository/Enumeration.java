package org.modeldriven.fuml.repository;

import java.util.List;


public interface Enumeration extends DataType {

	public fUML.Syntax.Classes.Kernel.Enumeration getDelegate();  
	
	public List<EnumerationLiteral> getOwnedLiteral();
    
} // Enumeration
