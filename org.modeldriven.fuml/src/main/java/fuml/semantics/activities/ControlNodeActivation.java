
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

public abstract class ControlNodeActivation extends
		fuml.semantics.activities.ActivityNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// By default, offer all tokens on all outgoing edges.

		Debug.println(this.node != null, "[fire] Control node " + this.node.name + "...");

		this.sendOffers(incomingTokens);
	} // fire

} // ControlNodeActivation
