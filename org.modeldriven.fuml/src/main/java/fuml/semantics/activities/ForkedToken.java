
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

public class ForkedToken extends
		fuml.semantics.activities.Token {

	public fuml.semantics.activities.Token baseToken = null;
	public int remainingOffersCount = 0;
	public boolean baseTokenIsWithdrawn = false;

	public boolean isControl() {
		// Test if the base token is a control token.

		return this.baseToken.isControl();
	} // isControl

	public void withdraw() {
		// If the base token is not withdrawn, then withdraw it.
		// Decrement the remaining offers count.
		// When the remaining number of offers is zero, then remove this token
		// from its holder.

		if (!this.baseTokenIsWithdrawn & !this.baseToken.isWithdrawn()) {
			this.baseToken.withdraw();

			// NOTE: This keeps a base token that is a forked token from being
			// withdrawn more than once, since withdrawing a forked token may
			// not actually remove it from its fork node holder.
			this.baseTokenIsWithdrawn = true;
		}

		if (this.remainingOffersCount > 0) {
			this.remainingOffersCount = this.remainingOffersCount - 1;
		}

		if (this.remainingOffersCount == 0) {
			super.withdraw();
		}
	} // withdraw

	public fuml.semantics.activities.Token copy() {
		// Return a copy of the base token.

		return this.baseToken.copy();
	} // copy

	public boolean equals(
			fuml.semantics.activities.Token otherToken) {
		// Test if this token is equal to another token.

		return this == otherToken;
	} // equals

	public fuml.semantics.values.Value getValue() {
		// Return the value of the base token.

		return this.baseToken.getValue();
	} // getValue

} // ForkedToken
