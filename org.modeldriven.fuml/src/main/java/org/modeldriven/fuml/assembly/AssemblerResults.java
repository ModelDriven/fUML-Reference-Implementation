package org.modeldriven.fuml.assembly;

import java.util.List;

import org.modeldriven.fuml.FumlObject;

import fUML.Syntax.Classes.Kernel.Element;

public interface AssemblerResults {
	public List<FumlObject> getResults();
	public List<String> getResultsXmiIds();
	public FumlObject lookupResult(String xmiId);
}
