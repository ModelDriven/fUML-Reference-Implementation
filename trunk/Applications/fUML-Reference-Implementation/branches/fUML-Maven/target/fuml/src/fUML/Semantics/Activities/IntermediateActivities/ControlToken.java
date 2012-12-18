
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
import fUML.Semantics.Loci.*;

public class ControlToken extends
		fUML.Semantics.Activities.IntermediateActivities.Token {

	public boolean equals(
			fUML.Semantics.Activities.IntermediateActivities.Token other) {
		// Return true if the other token is a control token, because control
		// tokens are interchangable.

		return other instanceof ControlToken;

	} // equals

	public fUML.Semantics.Activities.IntermediateActivities.Token copy() {
		// Return a new control token.

		return new ControlToken();
	} // copy

	public boolean isControl() {
		// Return true for a control token.

		return true;
	} // isControl

	public fUML.Semantics.Classes.Kernel.Value getValue() {
		// Control tokens do not have values.

		return null;
	} // getValue

} // ControlToken
