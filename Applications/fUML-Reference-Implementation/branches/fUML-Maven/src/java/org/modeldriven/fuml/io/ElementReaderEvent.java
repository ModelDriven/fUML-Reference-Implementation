package org.modeldriven.fuml.io;


public class ElementReaderEvent {

	private ElementReader elementReader;
	
	@SuppressWarnings("unused")
	private ElementReaderEvent() {}
	
	public ElementReaderEvent(ElementReader elementReader) {
		this.elementReader = elementReader;
	}
	
	public ElementReader getSource() {
		return this.elementReader;
	}
}
