package org.modeldriven.fuml.io;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.assembly.ElementAssemblerEventListener;
import org.modeldriven.fuml.xmi.validation.ValidationEventListener;

public abstract class ElementReader {
    
	protected List<ValidationEventListener> validationEventListeners;
	protected List<ElementAssemblerEventListener> elementAssemblerEventListeners;
	protected List<ElementReaderEventListener> elementReaderEventListeners;
    
    public void addValidationEventListener(ValidationEventListener eventListener) {
    	if (validationEventListeners == null)
    		validationEventListeners = new ArrayList<ValidationEventListener>();
    	this.validationEventListeners.add(eventListener);
    }
    
    public void removeValidationEventListener(ValidationEventListener eventListener) {
    	if (validationEventListeners == null)
    		return;
    	this.validationEventListeners.remove(eventListener);
    }
    
    public void addElementAssemblerEventListener(ElementAssemblerEventListener eventListener) {
    	if (elementAssemblerEventListeners == null)
    		elementAssemblerEventListeners = new ArrayList<ElementAssemblerEventListener>();
    	this.elementAssemblerEventListeners.add(eventListener);
    }
    
    public void removeElementAssemblerEventListener(ElementAssemblerEventListener eventListener) {
    	if (elementAssemblerEventListeners == null)
    		return;
    	this.elementAssemblerEventListeners.remove(eventListener);
    }

    public void addElementReaderEventListener(ElementReaderEventListener eventListener) {
    	if (elementReaderEventListeners == null)
    		elementReaderEventListeners = new ArrayList<ElementReaderEventListener>();
    	this.elementReaderEventListeners.add(eventListener);
    }
    
    public void removeElementReaderEventListener(ElementReaderEventListener eventListener) {
    	if (elementReaderEventListeners == null)
    		return;
    	this.elementReaderEventListeners.remove(eventListener);
    }
    
}
