/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.values.Value;
import fuml.syntax.activities.Activity;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterList;

public class ActivityExecution extends fuml.semantics.commonbehavior.Execution {

	public fuml.semantics.activities.ActivityNodeActivationGroup activationGroup = null;
	public Boolean isStreaming;

	public void execute() {
		// Execute the activity for this execution by creating an activity node
		// activation group and activating all the activity nodes in the
		// activity. If the activity has no stream parameters, then, when the
		// execution is complete, copy the values on the tokens offered by
		// output parameter nodes to the corresponding output parameters.
		
		Activity activity = (Activity) (this.getTypes().getValue(0));

		Debug.println("[execute] Activity " + activity.name + "...");
		// Debug.println("[execute] context = " + this.context.objectId());
		Debug.println("[event] Execute activity=" + activity.name);

		this.isStreaming = false;
		int i = 1;
		ParameterList parameters = activity.ownedParameter;
		while (i <= parameters.size() & !this.isStreaming) {
			Parameter parameter = parameters.getValue(i - 1);
			this.isStreaming = parameter.isStream;
			i = i + 1;
		}
		
		Debug.println("[execute] isStreaming = " + this.isStreaming);
		
		this.activationGroup = new ActivityNodeActivationGroup();
		this.activationGroup.activityExecution = this;
		this.activationGroup.activate(activity.node, activity.edge);

		if (!this.isStreaming) {
			this.complete();
		}
	} // execute
	
	public void complete() {
		// Copy the values on the tokens offered by output parameter nodes for
		// non-stream parameters to the corresponding output parameter values.
		
		Activity activity = (Activity) (this.getTypes().getValue(0));

		// Debug.println("[execute] Getting output parameter node activations...");

		ActivityParameterNodeActivationList outputActivations = this.activationGroup
				.getOutputParameterNodeActivations();

		// Debug.println("[execute] There are " + outputActivations.size() +
		// " output parameter node activations.");
		
		for (int i = 0; i < outputActivations.size(); i++) {
			ActivityParameterNodeActivation outputActivation = outputActivations
					.getValue(i);

			Parameter parameter = ((ActivityParameterNode) (outputActivation.node)).parameter;

			if (!parameter.isStream) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.parameter = parameter;
				
				TokenList tokens = outputActivation.getTokens();
				for (int j = 0; j < tokens.size(); j++) {
					Token token = tokens.getValue(j);
					Value value = ((ObjectToken) token).value;
					if (value != null) {
						parameterValue.values.addValue(value);
						Debug.println("[event] Output activity=" + activity.name
								+ " parameter=" + parameterValue.parameter.name
								+ " value=" + value);
					}
				}
	
				this.setParameterValue(parameterValue);
			}
		}

		Debug.println("[execute] Activity " + activity.name + " completed.");
	}

	public fuml.semantics.values.Value copy() {
		// Create a new activity execution that is a copy of this execution.
		// [Note: This currently just returns a non-executing execution for the
		// same activity as this execution.]

		return super.copy();
	} // copy

	public fuml.semantics.values.Value new_() {
		// Create a new activity execution with empty properties.

		return new ActivityExecution();
	} // new_

	public void terminate() {
		// Terminate all node activations. If this execution is non-streaming,
		// then this is sufficient to result in the activity execution ultimately
		// completing. Otherwise, explicitly complete the execution.

		if (this.activationGroup != null) { 
			this.activationGroup.terminateAll();
		}
		
		if (this.isStreaming) {
			this.complete();
		}
	} // terminate

} // ActivityExecution
