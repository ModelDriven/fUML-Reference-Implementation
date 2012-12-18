
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

public class ObjectToken extends
		fUML.Semantics.Activities.IntermediateActivities.Token {

	public fUML.Semantics.Classes.Kernel.Value value = null;

	public boolean equals(
			fUML.Semantics.Activities.IntermediateActivities.Token other) {
		// Test if this object token is the same as the other token.

		return this == other;
	} // equals

	public fUML.Semantics.Activities.IntermediateActivities.Token copy() {
		// Return a new object token with the same value as this token.
		// [Note: the holder of the copy is not set.]

		ObjectToken copy = new ObjectToken();
		copy.value = this.value;

		return copy;
	} // copy

	public boolean isControl() {
		// Return false for an object token.

		return false;
	} // isControl

	public fUML.Semantics.Classes.Kernel.Value getValue() {
		// Return the value of this object token.

		return this.value;
	} // getValue

} // ObjectToken
