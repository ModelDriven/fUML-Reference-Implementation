package org.modeldriven.fuml.assembly;

import fUML.Syntax.Classes.Kernel.Element;

public class ElementAssemblerEvent {

	private Element element;
	
	@SuppressWarnings("unused")
	private ElementAssemblerEvent() {}
	
	public ElementAssemblerEvent(Element element) {
		this.element = element;
	}
	
	public Element getSource() {
		return this.element;
	}
}
