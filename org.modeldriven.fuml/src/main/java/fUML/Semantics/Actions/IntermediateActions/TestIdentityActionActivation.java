
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

package fUML.Semantics.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class TestIdentityActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

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
