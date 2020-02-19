
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

import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.syntax.actions.CallOperationAction;
import fuml.syntax.classification.ParameterList;

public class CallOperationActionActivation extends
		fuml.semantics.actions.CallActionActivation {
	
	public boolean isReady() {
		// Check that this call operation action activation is ready to fire as a
		// call action activation and, in addition, that the input pin activation
		// for its target pin is ready to fire.
		
		boolean ready = super.isReady();
		if (ready) {
			CallOperationAction action = (CallOperationAction) (this.node);
			ready = this.getPinActivation(action.target).isReady();
		}
		return ready;
	}

	public fuml.semantics.commonbehavior.Execution getCallExecution() {
		// If the value on the target input pin is a reference, dispatch the
		// operation to it and return the resulting execution object.

		CallOperationAction action = (CallOperationAction) (this.node);
		Value target = this.takeTokens(action.target).getValue(0);

		Execution execution;
		if (target instanceof Reference) {
			execution = ((Reference) target).dispatch(action.operation);
		} else {
			execution = null;
		}

		return execution;

	} // getCallExecution

	@Override
	public ParameterList getParameters() {
		// Get the owned parameters of the operation of the call operation
		// action for this call operation action activation.
		
		return ((CallOperationAction) (this.node)).operation.ownedParameter;
	}

} // CallOperationActionActivation
