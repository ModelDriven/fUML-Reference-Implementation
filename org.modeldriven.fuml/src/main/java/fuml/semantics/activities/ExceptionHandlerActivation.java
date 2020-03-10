/*
 * Copyright 2020 Model Driven Solutions, Inc.
 * Copyright 2020 CEA LIST.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;
import fuml.semantics.actions.ActionActivation;
import fuml.semantics.actions.PinActivation;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.Pin;
import fuml.syntax.actions.Action;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.activities.ExceptionHandler;

public class ExceptionHandlerActivation {

	public ExceptionHandler handler;
	public ActionActivation declaringActionActivation;

	public void setExceptionhandler(ExceptionHandler exceptionHandler) {
		// Set the exception handler corresponding to the exception handler activation.
		
		this.handler = exceptionHandler;
	}

	public void setDeclaringActionActivation(ActionActivation activation) {
		// Set the activation for the action that declared the exception handler.
		
		this.declaringActionActivation = activation;
	}

	public boolean match(Value exception) {
		// Return true if the given exception is an instance of an exception type
		// of the exception handler for this exception handler activation.
		
		boolean match = false;
		int i = 1;
		while (!match & i <= this.handler.exceptionType.size()) {
			match = exception.isInstanceOf(this.handler.exceptionType.getValue(i - 1));
			i = i + 1;
		}
		return match;
	}

	public void handle(Value exception) {
		// Offer the given exception to the exception handler body on its
		// exception input pin. After the body fires, transfer its outputs
		// to the output pins of the declaring action activation.
		
		Debug.println("[handle] action = " + this.declaringActionActivation.node.name + 
				      ", exception = " + exception);
		
		ActivityNodeActivationGroup group = this.declaringActionActivation.group;
		ActionActivation handlerBodyActivation = 
				(ActionActivation) group.getNodeActivation(this.handler.handlerBody);
		PinActivation pinActivation = 
				handlerBodyActivation.getPinActivation((Pin) this.handler.exceptionInput);
		
		ObjectToken token = new ObjectToken();
		token.value = exception;
		pinActivation.addToken(token);
		
		handlerBodyActivation.receiveOffer();
		this.transferOutputs(handlerBodyActivation);
	}

	public void transferOutputs(ActionActivation handlerBodyActivation) {
		// Transfer the output values from given activation of the exception
		// handler body to the output pins of the declaring action activation.
		
		OutputPinList sourceOutputs = ((Action) handlerBodyActivation.node).output;
		OutputPinList targetOutputs = ((Action) this.declaringActionActivation.node).output;
		
		for (int i = 0; i < sourceOutputs.size(); i++) {
			PinActivation sourcePinActivation = 
					handlerBodyActivation.getPinActivation(sourceOutputs.getValue(i));
			OutputPin targetPin = targetOutputs.getValue(i);
			
			ValueList values = new ValueList();
			TokenList tokens = sourcePinActivation.takeTokens();
			for (int j = 0; j < tokens.size(); j++) {
				Token token = tokens.getValue(j);
				values.addValue(token.getValue());
			}
			
			this.declaringActionActivation.putTokens(targetPin, values);
		}
	}

}
