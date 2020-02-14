
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 CEA LIST.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.syntax.actions.CallAction;
import fuml.syntax.actions.InputPinList;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;

public abstract class CallActionActivation extends
		fuml.semantics.actions.InvocationActionActivation {

	public fuml.semantics.commonbehavior.ExecutionList callExecutions = new fuml.semantics.commonbehavior.ExecutionList();

	public void doAction() {
		// Get the call execution object, set its input parameters from the
		// argument pins and execute it.
		// Once execution completes, copy the values of the output parameters of
		// the call execution to the result pins of the call action execution,
		// then destroy the execution.

		Execution callExecution = this.getCallExecution();

		if (callExecution != null) {
			this.callExecutions.addValue(callExecution);

			CallAction callAction = (CallAction) (this.node);
			InputPinList argumentPins = callAction.argument;
			OutputPinList resultPins = callAction.result;

			ParameterList parameters = callExecution.getBehavior().ownedParameter;

			int pinNumber = 1;
			int i = 1;
			while (i <= parameters.size()) {
				Parameter parameter = parameters.getValue(i - 1);
				if (parameter.direction == ParameterDirectionKind.in
						| parameter.direction == ParameterDirectionKind.inout) {
					ParameterValue parameterValue = new ParameterValue();
					parameterValue.parameter = parameter;
					parameterValue.values = this.takeTokens(argumentPins
							.getValue(pinNumber - 1));
					callExecution.setParameterValue(parameterValue);
					pinNumber = pinNumber + 1;
				}
				i = i + 1;
			}

			callExecution.execute();
			
			if(callExecution.exception == null) {
				ParameterValueList outputParameterValues = callExecution
						.getOutputParameterValues();
	
				pinNumber = 1;
				i = 1;
				while (i <= parameters.size()) {
					Parameter parameter = parameters.getValue(i - 1);
					if ((parameter.direction == ParameterDirectionKind.inout)
							| (parameter.direction == ParameterDirectionKind.out)
							| (parameter.direction == ParameterDirectionKind.return_)) {
						for (int j = 0; j < outputParameterValues.size(); j++) {
							ParameterValue outputParameterValue = outputParameterValues
									.getValue(j);
							if (outputParameterValue.parameter == parameter) {
								OutputPin resultPin = resultPins
										.getValue(pinNumber - 1);
								this.putTokens(resultPin,
										outputParameterValue.values);
							}
						}
						pinNumber = pinNumber + 1;
					}
					i = i + 1;
				}
			} else {
				this.propagateException(callExecution.exception);
			}

			callExecution.destroy();
			this.removeCallExecution(callExecution);
		}
	} // doAction

	public abstract fuml.semantics.commonbehavior.Execution getCallExecution();

	public void terminate() {
		// Terminate all call executions (if any), then terminate the call
		// action activation (self).

		for (int i = 0; i < this.callExecutions.size(); i++) {
			Execution execution = this.callExecutions.getValue(i);
			execution.terminate();
		}

		super.terminate();
	} // terminate

	public void removeCallExecution(
			fuml.semantics.commonbehavior.Execution execution) {
		// Remove the given execution from the current list of call executions.

		boolean notFound = true;
		int i = 1;
		while (notFound & i <= this.callExecutions.size()) {
			if (this.callExecutions.getValue(i - 1) == execution) {
				this.callExecutions.removeValue(i - 1);
				notFound = false;
			}
			i = i + 1;
		}
	} // removeCallExecution

} // CallActionActivation
