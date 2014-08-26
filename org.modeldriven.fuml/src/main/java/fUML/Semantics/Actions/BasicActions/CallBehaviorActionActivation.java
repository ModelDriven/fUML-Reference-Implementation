
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

package fUML.Semantics.Actions.BasicActions;

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
import fUML.Semantics.Loci.*;

public class CallBehaviorActionActivation extends
		fUML.Semantics.Actions.BasicActions.CallActionActivation {

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution getCallExecution() {
		// Create and execution for the given behavior at the current locus and
		// return the resulting execution object.
		// If the given behavior is in the context of a classifier, then pass
		// the current context object as the context for the call.
		// Otherwise, use a null context.
		// [Note that this requires the behavior context to be compatible with
		// the type of the current contect object.]

		Behavior behavior = ((CallBehaviorAction) (this.node)).behavior;

		Object_ context;
		if (behavior.context == null) {
			context = null;
		} else {
			// Debug.println("[getCallExecution] behavior context = " +
			// behavior.context.name);
			context = this.getExecutionContext();
		}

		// Debug.println("[getCallExecution] context = " + context);

		return this.getExecutionLocus().factory.createExecution(behavior,
				context);

	} // getCallExecution

} // CallBehaviorActionActivation
