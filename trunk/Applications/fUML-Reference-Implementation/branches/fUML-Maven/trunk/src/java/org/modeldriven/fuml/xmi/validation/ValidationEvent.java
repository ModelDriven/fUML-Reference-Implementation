package org.modeldriven.fuml.xmi.validation;

public class ValidationEvent {
    
	private ValidationErrorCollector errorCollector;
	
	@SuppressWarnings("unused")
	private ValidationEvent() {}
	
	public ValidationEvent(ValidationErrorCollector errorCollector) {
		this.errorCollector = errorCollector;
	}

	public ValidationErrorCollector getErrorCollector() {
		return errorCollector;
	}
}
