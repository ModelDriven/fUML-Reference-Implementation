
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

package fuml.semantics.actions;

import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.ReadIsClassifiedObjectAction;

public class ReadIsClassifiedObjectActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the value on the object input pin and determine if it is
		// classified by the classifier specified in the action.
		// If the isDirect attribute of the action is false, then place true on
		// the result output pin if the input object has the specified
		// classifier or of one its (direct or indirect) descendants as a type.
		// If the isDirect attribute of the action is true, then place true on
		// the result output pin if the input object has the specified
		// classifier as a type.
		// Otherwise place false on the result output pin.

		ReadIsClassifiedObjectAction action = (ReadIsClassifiedObjectAction) (this.node);

		Value input = this.takeTokens(action.object).getValue(0);

		boolean result = false;		
		if (action.isDirect) {
			result = input.hasType(action.classifier);
		} else {
			result = input.isInstanceOf(action.classifier);
		}

		ValueList values = new ValueList();
		values.addValue(this.makeBooleanValue(result));

		this.putTokens(action.result, values);
	} // doAction

} // ReadIsClassifiedObjectActionActivation
