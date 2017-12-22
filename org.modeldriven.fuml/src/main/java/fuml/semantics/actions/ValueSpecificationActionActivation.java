
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

import fuml.semantics.classification.Value;
import fuml.syntax.actions.ValueSpecificationAction;

public class ValueSpecificationActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Evaluate the value specification for the action and place the result
		// on the result pin of the action.

		ValueSpecificationAction action = (ValueSpecificationAction) (this.node);

		Value value = this.getExecutionLocus().executor.evaluate(action.value);
		this.putToken(action.result, value);

	} // doAction

} // ValueSpecificationActionActivation
