package org.modeldriven.fuml.xmi.validation;

public class ValidationErrorEvent extends ValidationEvent {

	private ValidationError error;
	
	@SuppressWarnings("unused")
	private ValidationErrorEvent() {
		super(null); // not used
	}
	
	public ValidationErrorEvent(ValidationError error, 
			ValidationErrorCollector errorCollector) {
		super(errorCollector);
		this.error = error;
	}

	public ValidationError getError() {
		return error;
	}
}
