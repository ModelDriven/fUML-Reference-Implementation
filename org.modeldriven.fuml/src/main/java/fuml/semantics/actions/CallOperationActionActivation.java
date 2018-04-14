
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

import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.syntax.actions.CallOperationAction;

public class CallOperationActionActivation extends
		fuml.semantics.actions.CallActionActivation {

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

} // CallOperationActionActivation
