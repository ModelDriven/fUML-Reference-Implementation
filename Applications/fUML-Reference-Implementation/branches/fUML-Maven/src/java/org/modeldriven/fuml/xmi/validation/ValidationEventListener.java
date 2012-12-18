package org.modeldriven.fuml.xmi.validation;

import java.util.EventListener;

public interface ValidationEventListener extends EventListener {
    public void validationStarted(ValidationEvent event);
    public void validationCompleted(ValidationEvent event);
    public void validationError(ValidationErrorEvent event);
}
