
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.BasicActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Loci.*;

public abstract class CallActionActivation extends
		fUML.Semantics.Actions.BasicActions.InvocationActionActivation {

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ExecutionList callExecutions = new fUML.Semantics.CommonBehaviors.BasicBehaviors.ExecutionList();

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

			callExecution.destroy();
			this.removeCallExecution(callExecution);
		}
	} // doAction

	public abstract fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution getCallExecution();

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
			fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution execution) {
		// Remove the given execution from the current list of call executions.

		boolean notFound = true;
		int i = 1;
		while (notFound & i <= this.callExecutions.size()) {
			if (this.callExecutions.getValue(i - 1) == execution) {
				this.callExecutions.removeValue(i - 1);
				notFound = false;
			}
		}
	} // removeCallExecution

} // CallActionActivation
