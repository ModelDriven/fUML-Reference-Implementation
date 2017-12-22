
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

import fuml.semantics.structuredclassifiers.Reference;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.ReadSelfAction;

public class ReadSelfActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the context object of the activity execution containing this
		// action activation and place a reference to it on the result output
		// pin.

		// Debug.println("[ReadSelfActionActivation] Start...");

		Reference context = new Reference();
		context.referent = this.getExecutionContext();

		// Debug.println("[ReadSelfActionActivation] context object = " +
		// context.referent);

		OutputPin resultPin = ((ReadSelfAction) (this.node)).result;
		this.putToken(resultPin, context);
	} // doAction

} // ReadSelfActionActivation
