
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

public class ObjectToken extends
		fuml.semantics.activities.Token {

	public fuml.semantics.values.Value value = null;

	public boolean equals(
			fuml.semantics.activities.Token other) {
		// Test if this object token is the same as the other token.

		return this == other;
	} // equals

	public fuml.semantics.activities.Token copy() {
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

	public fuml.semantics.values.Value getValue() {
		// Return the value of this object token.

		return this.value;
	} // getValue

} // ObjectToken
