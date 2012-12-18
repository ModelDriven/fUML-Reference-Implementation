
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

public abstract class Token extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation holder = null;

	public fUML.Semantics.Activities.IntermediateActivities.Token transfer(
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation holder) {
		// if this token does not have any holder, make the given holder its
		// holder.
		// Otherwise, remove this token from its holder and return a copy of it
		// transfered to a new holder.

		Token token = this;
		if (this.holder != null) {
			this.withdraw();
			token = this.copy();
		}

		token.holder = holder;
		return token;
	} // transfer

	public void withdraw() {
		// Remove this token from its holder, withdrawing any offers for it.

		if (!this.isWithdrawn()) {
			// Debug.println("[withdraw] Taking token with value = " +
			// this.getValue());
			this.holder.removeToken(this);
			this.holder = null;
		}
	} // withdraw

	public abstract boolean equals(
			fUML.Semantics.Activities.IntermediateActivities.Token other);

	public abstract fUML.Semantics.Activities.IntermediateActivities.Token copy();

	public boolean isWithdrawn() {
		// Test if this token has been withdrawn.

		return this.holder == null;
	} // isWithdrawn

	public abstract boolean isControl();

	public abstract fUML.Semantics.Classes.Kernel.Value getValue();

} // Token
