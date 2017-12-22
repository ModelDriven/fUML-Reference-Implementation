
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

package fuml.semantics.activities;

import fuml.Debug;

public class FlowFinalNodeActivation extends
		fuml.semantics.activities.ControlNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// Consume all incoming tokens.

		Debug.println("[fire] Flow final node " + this.node.name + "...");

		for (int i = 0; i < incomingTokens.size(); i++) {
			Token token = incomingTokens.getValue(i);
			token.withdraw();
		}
	} // fire

} // FlowFinalNodeActivation
