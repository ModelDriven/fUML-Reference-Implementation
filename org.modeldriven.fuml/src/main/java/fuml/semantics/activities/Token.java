
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

public abstract class Token extends org.modeldriven.fuml.FumlObject {

	public fuml.semantics.activities.ActivityNodeActivation holder = null;

	public fuml.semantics.activities.Token transfer(
			fuml.semantics.activities.ActivityNodeActivation holder) {
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
			fuml.semantics.activities.Token other);

	public abstract fuml.semantics.activities.Token copy();

	public boolean isWithdrawn() {
		// Test if this token has been withdrawn.

		return this.holder == null;
	} // isWithdrawn

	public abstract boolean isControl();

	public abstract fuml.semantics.classification.Value getValue();

} // Token
