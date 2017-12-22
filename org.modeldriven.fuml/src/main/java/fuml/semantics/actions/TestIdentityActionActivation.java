
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
import fuml.syntax.actions.TestIdentityAction;

public class TestIdentityActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the values from the first and second input pins and test if they
		// are equal. (Note the equality of references is defined to be that
		// they have identical referents.)
		// If they are equal, place true on the pin execution for the result
		// output pin, otherwise place false.

		TestIdentityAction action = (TestIdentityAction) (this.node);

		Value firstValue = this.takeTokens(action.first).getValue(0);
		Value secondValue = this.takeTokens(action.second).getValue(0);

		Value testResult = this
				.makeBooleanValue(firstValue.equals(secondValue));
		this.putToken(action.result, testResult);

	} // doAction

} // TestIdentityActionActivation
