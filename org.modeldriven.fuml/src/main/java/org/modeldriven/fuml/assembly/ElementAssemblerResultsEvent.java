package org.modeldriven.fuml.assembly;


public class ElementAssemblerResultsEvent {

	private AssemblerResults results;
	
	@SuppressWarnings("unused")
	private ElementAssemblerResultsEvent() {}
	
	public ElementAssemblerResultsEvent(AssemblerResults results) {
		this.results = results;
	}
	
	public AssemblerResults getSource() {
		return this.results;
	}
}
