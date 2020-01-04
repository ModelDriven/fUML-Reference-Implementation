
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

package fuml.semantics.actions;

import fuml.semantics.activities.ActivityNodeActivationGroup;
import fuml.semantics.activities.TokenList;
import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.commonbehavior.StreamingParameterValue;
import fuml.syntax.actions.CallAction;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.InputPinList;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.activities.ActivityNode;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;

public abstract class CallActionActivation extends
		fuml.semantics.actions.InvocationActionActivation {

	public fuml.semantics.commonbehavior.ExecutionList callExecutions = new fuml.semantics.commonbehavior.ExecutionList();
	public Boolean isStreaming;
	
	public void initialize(ActivityNode node, ActivityNodeActivationGroup group) {
		// Initialize this call action activation to be not streaming.
		
		super.initialize(node, group);
		this.isStreaming = false;
	}

	public void doAction() {
		// Get the call execution object, set its input parameters from the
		// argument pins and execute it.
		// If there are no streaming input parameters, then, once execution completes, 
		// copy the values of the output parameters of the call execution to the result 
		// pins of the call action execution and destroy the execution.
		// If there are streaming input parameters, then leave the call execution object
		// in place to process any additional inputs that may be posted to the streaming
		// input parameters.

		Execution callExecution = this.getCallExecution();

		if (callExecution != null) {
			this.callExecutions.addValue(callExecution);

			CallAction callAction = (CallAction) (this.node);
			InputPinList argumentPins = callAction.argument;
			OutputPinList resultPins = callAction.result;

			ParameterList parameters = callExecution.getBehavior().ownedParameter;

			int pinNumber = 1;
			int outputPinNumber = 1;
			int i = 1;
			this.isStreaming = false;
			while (i <= parameters.size()) {
				Parameter parameter = parameters.getValue(i - 1);
				if (parameter.direction == ParameterDirectionKind.in
						| parameter.direction == ParameterDirectionKind.inout) {
					InputPin argumentPin = argumentPins.getValue(pinNumber - 1);
					ParameterValue parameterValue;
					if (parameter.isStream) {
						this.isStreaming = true;
						parameterValue = new StreamingParameterValue();
						parameterValue.values = this.getTokens(argumentPin);
						InputPinActivation argumentPinActivation = 
							(InputPinActivation) this.getPinActivation(argumentPin);
						argumentPinActivation.streamingParameterValue = 
							(StreamingParameterValue)parameterValue;
					} else {
						parameterValue = new ParameterValue();
						parameterValue.values = this.takeTokens(argumentPin);
					}
					parameterValue.parameter = parameter;
					callExecution.setParameterValue(parameterValue);
					pinNumber = pinNumber + 1;
				} 
				if (parameter.direction == ParameterDirectionKind.out
						| parameter.direction == ParameterDirectionKind.inout) {
					if (parameter.isStream) {
						ParameterValue parameterValue = new StreamingParameterValue();
						parameterValue.parameter = parameter;
						PinStreamingParameterListener listener = 
							new PinStreamingParameterListener();
						listener.nodeActivation = this.getPinActivation(
								resultPins.getValue(outputPinNumber - 1));
						((StreamingParameterValue)parameterValue).register(listener);
						
						// Note: Add a new parameter value, so that there will
						// be two separate input and output parameter values for a
						// streaming inout parameter.
						callExecution.parameterValues.addValue(parameterValue);
					}
					outputPinNumber = outputPinNumber + 1;
				}
				i = i + 1;
			}

			callExecution.execute();

			if (!isStreaming) {
				this.completeCall(callExecution);
			}
		}
	} // doAction
	
	public TokenList completeAction() {
		// If this call action activation is not streaming, then complete the action
		// normally. Otherwise, complete the action without checking for firing again
		// (but keep the call execution running).
		
		TokenList incomingTokens;
		if (this.isStreaming) {
			incomingTokens = new TokenList();
		} else {
			incomingTokens = super.completeAction();
		}
		return incomingTokens;
	}
	
	public void completeCall(Execution callExecution) {
		// Copy the values of the output parameters of the call execution to the result 
		// pin activations of the call action activation and destroy the execution.

		CallAction callAction = (CallAction) (this.node);
		OutputPinList resultPins = callAction.result;
		ParameterList parameters = callExecution.getBehavior().ownedParameter;

		ParameterValueList outputParameterValues = callExecution
				.getOutputParameterValues();

		int pinNumber = 1;
		int i = 1;
		while (i <= parameters.size()) {
			Parameter parameter = parameters.getValue(i - 1);
			if ((parameter.direction == ParameterDirectionKind.inout)
					| (parameter.direction == ParameterDirectionKind.out)
					| (parameter.direction == ParameterDirectionKind.return_)) {
				if (!parameter.isStream) {
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
				}
				pinNumber = pinNumber + 1;
			}
			i = i + 1;
		}

		callExecution.destroy();
		this.removeCallExecution(callExecution);
	}

	public abstract fuml.semantics.commonbehavior.Execution getCallExecution();

	public void terminate() {
	// Terminate all call executions (if any). If this call action
	// activation is streaming, complete the call before terminating the call  
	// execution. Finally, terminate the call action activation itself.
	
	for (int i = 0; i < this.callExecutions.size(); i++) {
		Execution execution = this.callExecutions.getValue(i);
		execution.terminate();
		if (this.isStreaming) {
			// Note: If the call is streaming, then isLocallyReentrant = false and
			// there should be at most one call execution.
			this.completeCall(execution);
			super.completeAction();
		}
	}

	super.terminate();
	} // terminate

	public void removeCallExecution(fuml.semantics.commonbehavior.Execution execution) {
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
