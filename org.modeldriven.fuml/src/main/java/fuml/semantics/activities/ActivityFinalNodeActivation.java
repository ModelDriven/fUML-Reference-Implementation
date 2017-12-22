
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
import fuml.semantics.actions.ExpansionActivationGroup;

public class ActivityFinalNodeActivation extends
		fuml.semantics.activities.ControlNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// Terminate the activity execution or structured node activation
		// containing this activation.

		Debug.println("[fire] Activity final node " + this.node.name + "...");

		if (incomingTokens.size() > 0 | this.incomingEdges.size() == 0) {
			if (this.group.activityExecution != null) {
				this.group.activityExecution.terminate();
			} else if (this.group.containingNodeActivation != null) {
				this.group.containingNodeActivation.terminateAll();
			} else if (this.group instanceof ExpansionActivationGroup) {
				((ExpansionActivationGroup) this.group).regionActivation
						.terminate();
			}
		}
	} // fire

} // ActivityFinalNodeActivation
