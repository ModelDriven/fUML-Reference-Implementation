
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

package fUML.Semantics.Activities.IntermediateActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Activities.ExtraStructuredActivities.*;
import fUML.Semantics.Loci.*;

public class ActivityFinalNodeActivation extends
		fUML.Semantics.Activities.IntermediateActivities.ControlNodeActivation {

	public void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
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
