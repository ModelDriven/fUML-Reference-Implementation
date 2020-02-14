/*****************************************************************************
* Copyright (c) 2020 CEA LIST.
*
* Licensed under the Academic Free License version 3.0 
* (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
* in the file entitled Licensing-Information. 
*
* Contributors:
*  Jeremie TATIBOUET <jeremie.tatibouet@cea.fr>
*
*****************************************************************************/
package fuml.semantics.activities;

import fuml.semantics.actions.ActionActivation;
import fuml.semantics.actions.PinActivation;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.Pin;
import fuml.syntax.actions.Action;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.activities.ExceptionHandler;

public class ExceptionHandlerActivation {

	public ExceptionHandler handler;

	public ActionActivation declaringActionActivation;

	public void setExceptionhandler(ExceptionHandler exceptionHandler) {
		this.handler = exceptionHandler;
	}

	public void setDeclaringActionActivation(ActionActivation activation) {
		this.declaringActionActivation = activation;
	}

	public boolean match(Value exception) {
		boolean match = false;
		int i = 0;
		while (!match && i < this.handler.exceptionType.size()) {
			match = exception.isInstanceOf(this.handler.exceptionType.getValue(i));
			i = i + 1;
		}
		return match;
	}

	public void handle(Value exception) {
		ActivityNodeActivationGroup group = this.declaringActionActivation.group;
		ActionActivation handlerBodyActivation = (ActionActivation) group.getNodeActivation(handler.handlerBody);
		PinActivation pinActivation = handlerBodyActivation.getPinActivation((Pin) handler.exceptionInput);
		ObjectToken token = new ObjectToken();
		token.value = exception;
		pinActivation.addToken(token);
		handlerBodyActivation.receiveOffer();
		transferOutputs(handlerBodyActivation);
	}

	public void transferOutputs(ActionActivation handlerBodyActivation) {
		OutputPinList sourceOutputs = ((Action) handlerBodyActivation.node).output;
		OutputPinList targetOutputs = ((Action) declaringActionActivation.node).output;
		for (int i = 0; i < sourceOutputs.size(); i++) {
			PinActivation sourcePinActivation = handlerBodyActivation.getPinActivation(sourceOutputs.getValue(i));
			ValueList values = new ValueList();
			TokenList tokens = sourcePinActivation.takeTokens();
			for (int j = 0; j < tokens.size(); j++) {
				values.addValue(tokens.getValue(j).getValue());
			}
			declaringActionActivation.putTokens(targetOutputs.getValue(i), values);
		}
	}

}
