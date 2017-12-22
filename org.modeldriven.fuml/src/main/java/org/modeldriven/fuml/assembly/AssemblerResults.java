package org.modeldriven.fuml.assembly;

import java.util.List;

import org.modeldriven.fuml.FumlObject;

import fuml.syntax.commonstructure.Element;

public interface AssemblerResults {
	public List<FumlObject> getResults();
	public List<String> getResultsXmiIds();
	public FumlObject lookupResult(String xmiId);
}
