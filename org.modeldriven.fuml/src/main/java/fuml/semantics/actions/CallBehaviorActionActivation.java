
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.structuredclassifiers.Object_;
import fuml.syntax.actions.CallBehaviorAction;
import fuml.syntax.classification.ParameterList;
import fuml.syntax.commonbehavior.Behavior;

public class CallBehaviorActionActivation extends
		fuml.semantics.actions.CallActionActivation {

	public fuml.semantics.commonbehavior.Execution getCallExecution() {
		// Create and execution for the given behavior at the current locus and
		// return the resulting execution object.
		// If the given behavior is in the context of a classifier, then pass
		// the current context object as the context for the call.
		// Otherwise, use a null context.
		// [Note that this requires the behavior context to be compatible with
		// the type of the current contect object.]

		Behavior behavior = ((CallBehaviorAction) (this.node)).behavior;

		Object_ context;
		if (behavior.context == null) {
			context = null;
		} else {
			// Debug.println("[getCallExecution] behavior context = " +
			// behavior.context.name);
			context = this.getExecutionContext();
		}

		// Debug.println("[getCallExecution] context = " + context);

		return this.getExecutionLocus().factory.createExecution(behavior,
				context);

	} // getCallExecution

	@Override
	public ParameterList getParameters() {
		// Get the owned parameters of the behavior of the call behavior
		// action for this call behavior action activation.
		
		return ((CallBehaviorAction) (this.node)).behavior.ownedParameter;
	}

} // CallBehaviorActionActivation
