
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

package fUML.Semantics.Activities.IntermediateActivities;

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
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ActivityExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution {

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationGroup activationGroup = null;

	public void execute() {
		// Execute the activity for this execution by creating an activity node
		// activation group and activating all the activity nodes in the
		// activity.
		// When this is complete, copy the values on the tokens offered by
		// output parameter nodes to the corresponding output parameters.

		Activity activity = (Activity) (this.getTypes().getValue(0));

		Debug.println("[execute] Activity " + activity.name + "...");
		// Debug.println("[execute] context = " + this.context.objectId());
		Debug.println("[event] Execute activity=" + activity.name);

		this.activationGroup = new ActivityNodeActivationGroup();
		this.activationGroup.activityExecution = this;
		this.activationGroup.activate(activity.node, activity.edge);

		// Debug.println("[execute] Getting output parameter node activations...");

		ActivityParameterNodeActivationList outputActivations = this.activationGroup
				.getOutputParameterNodeActivations();

		// Debug.println("[execute] There are " + outputActivations.size() +
		// " output parameter node activations.");

		for (int i = 0; i < outputActivations.size(); i++) {
			ActivityParameterNodeActivation outputActivation = outputActivations
					.getValue(i);

			ParameterValue parameterValue = new ParameterValue();
			parameterValue.parameter = ((ActivityParameterNode) (outputActivation.node)).parameter;

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

		Debug.println("[execute] Activity " + activity.name + " completed.");
	} // execute

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new activity execution that is a copy of this execution.
		// [Note: This currently just returns a non-executing execution for the
		// same activity as this execution.]

		return super.copy();
	} // copy

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new activity execution with empty properties.

		return new ActivityExecution();
	} // new_

	public void terminate() {
		// Terminate all node activations (which will ultimately result in the
		// activity execution completing).

		this.activationGroup.terminateAll();
	} // terminate

} // ActivityExecution
