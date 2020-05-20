
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 * Copyright 2020 CEA LIST
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
	
	public OutputPinList nonStreamingOutputPins = new OutputPinList();
	public ParameterList nonStreamingOutputParameters = new ParameterList();
	
	public void initialize(ActivityNode node, ActivityNodeActivationGroup group) {
		// Initialize this call action activation to be not streaming.
		
		super.initialize(node, group);
		this.isStreaming = false;
	}
	
	public boolean isReady() {
		// Check that this call action activation is ready to fire, accounting for
		// the possibility of pins corresponding to streaming parameters. In order
		// to be ready, only argument pin activations for non-streaming parameters must
		// be ready, except if all the argument pin activations are for streaming
		// parameters with multiplicity lower bound greater than 0, in which case
		// at least one of those pins must have an offered value. 
		
		boolean ready = this.isControlReady();
		
		CallAction callAction = (CallAction) (this.node);
		InputPinList argumentPins = callAction.argument;
		
		if (ready & argumentPins.size() > 0) {
			ParameterList parameters = this.getParameters();
			ParameterList inputParameters = new ParameterList();
			for (int i = 0; i < parameters.size(); i++) {
				Parameter parameter = parameters.getValue(i);
				if (parameter.direction == ParameterDirectionKind.in
						| parameter.direction == ParameterDirectionKind.inout) {
					inputParameters.addValue(parameter);
				}				
			}
			
			boolean streamingReady = false;
			int j = 1;
			while (ready & j <= argumentPins.size()) {
				InputPin argumentPin = argumentPins.getValue(j - 1);
				InputPinActivation pinActivation = 
						(InputPinActivation)this.getPinActivation(argumentPin);
				if (j > inputParameters.size()) {
					ready = pinActivation.isReady();
				}
				boolean isStream = false;
				if (j <= inputParameters.size()) {
					isStream = inputParameters.getValue(j - 1).isStream;
				}
				if (!isStream) {
					// If there are any non-streaming argument pins, then streaming
					// is considered to be ready.
					streamingReady = true;

					// All non-streaming argument pins must be ready.
					ready = pinActivation.isReady();
				} else if (pinActivation.isReadyForStreaming()) {
					// If there are only streaming argument pins, then streaming
					// is ready if any of them are ready for streaming.
					streamingReady = true;
				}
				j = j + 1;
			}
			
			ready = ready & streamingReady;
		}
		
		return ready;
	}
	
	public void doAction() {
		// Get the call execution object, set its input parameters from the
		// argument pins and execute it.
		// If there are no streaming input parameters, then, once execution completes, 
		// if the execution raised an exception, then propagate the exception.
		// Otherwise, copy the values of the output parameters of the call execution to 
		// the result pins of the call action execution. In either canse, destroy the 
		// execution.
		// If there are streaming input parameters, then leave the call execution object
		// in place to process any additional inputs that may be posted to the streaming
		// input parameters.

		Execution callExecution = this.getCallExecution();

		if (callExecution != null) {
			this.callExecutions.addValue(callExecution);

			CallAction callAction = (CallAction) (this.node);
			InputPinList argumentPins = callAction.argument;
			OutputPinList resultPins = callAction.result;

			// Must get parameters from call execution behavior, to ensure the correct
			// parameters are used for an operation method.
			ParameterList parameters = callExecution.getBehavior().ownedParameter;

			int pinNumber = 1;
			int outputPinNumber = 1;
			int i = 1;
			InputPinActivation streamingPinActivation = null;
			this.nonStreamingOutputPins.clear();
			this.nonStreamingOutputParameters.clear();
			while (i <= parameters.size()) {
				Parameter parameter = parameters.getValue(i - 1);
				if (parameter.direction == ParameterDirectionKind.in
						| parameter.direction == ParameterDirectionKind.inout) {
					InputPin argumentPin = argumentPins.getValue(pinNumber - 1);
					ParameterValue parameterValue;
					if (parameter.isStream) {
						parameterValue = new StreamingParameterValue();
						parameterValue.values = this.getTokens(argumentPin);
						streamingPinActivation = 
							(InputPinActivation) this.getPinActivation(argumentPin);
						streamingPinActivation.streamingParameterValue = 
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
						| parameter.direction == ParameterDirectionKind.inout
						| parameter.direction == ParameterDirectionKind.return_) {
					OutputPin resultPin = resultPins.getValue(outputPinNumber - 1);
					if (!parameter.isStream) {
						this.nonStreamingOutputPins.addValue(resultPin);
						this.nonStreamingOutputParameters.addValue(parameter);
					} else {
						ParameterValue parameterValue = new StreamingParameterValue();
						parameterValue.parameter = parameter;
						PinStreamingParameterListener listener = 
							new PinStreamingParameterListener();
						listener.nodeActivation = this.getPinActivation(resultPin);
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
			
			if (streamingPinActivation == null) {
				this.isStreaming = false;
			} else {
				this.isStreaming = !streamingPinActivation.streamingIsTerminated();
			}
			
			if (!this.isStreaming) {
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
		// If the call execution raised an exception, then propagate it. Otherwise,
		// copy the values of the non-streaming output parameters of the call execution 
		// to the corresponding result pin activations of the call action activation. 
		// In either case, destroy the execution.

		if(callExecution.exception != null) {
			this.propagateException(callExecution.exception);
		} else {
			OutputPinList resultPins = this.nonStreamingOutputPins;
			ParameterList parameters = this.nonStreamingOutputParameters;
	
			ParameterValueList outputParameterValues = callExecution
					.getOutputParameterValues();
			
			for (int i = 0; i < resultPins.size(); i++) {
				OutputPin resultPin = resultPins.getValue(i);
				Parameter parameter = parameters.getValue(i);
				for (int j = 0; j < outputParameterValues.size(); j++) {
					ParameterValue outputParameterValue = outputParameterValues
							.getValue(j);
					if (outputParameterValue.parameter == parameter) {
						this.putTokens(resultPin, outputParameterValue.values);
					}
				}
			}
		}

		callExecution.destroy();
		this.removeCallExecution(callExecution);
	}
	
	public void completeStreamingCall() {
		// Complete a streaming call execution and then complete this call action activation.

		if (this.callExecutions.size() > 0) {
			// Note: If the call is streaming, then isLocallyReentrant = false and
			// there should be at most one call execution.
			this.completeCall(this.callExecutions.getValue(0));
			super.completeAction();
		}		
	}
	
	public OutputPinList getOfferingOutputPins() {
		// Only send offers from output pins that correspond to non-streaming parameters.
		
		return this.nonStreamingOutputPins;
	}
	
	public abstract ParameterList getParameters();

	public abstract Execution getCallExecution();

	public void terminate() {
		// Terminate all call executions (if any). If this call action
		// activation is streaming, complete the call before terminating the call  
		// execution. Finally, terminate the call action activation itself.
		
		if (this.isStreaming) {
			this.completeStreamingCall();
		} else {
			for (int i = 0; i < this.callExecutions.size(); i++) {
				Execution execution = this.callExecutions.getValue(i);
				execution.terminate();
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
