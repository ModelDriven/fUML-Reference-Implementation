
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

public class CallOperationActionActivation extends
		fUML.Semantics.Actions.BasicActions.CallActionActivation {

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution getCallExecution() {
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
