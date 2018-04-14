
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

public class ControlToken extends
		fuml.semantics.activities.Token {

	public boolean equals(
			fuml.semantics.activities.Token other) {
		// Return true if the other token is a control token, because control
		// tokens are interchangable.

		return other instanceof ControlToken;

	} // equals

	public fuml.semantics.activities.Token copy() {
		// Return a new control token.

		return new ControlToken();
	} // copy

	public boolean isControl() {
		// Return true for a control token.

		return true;
	} // isControl

	public fuml.semantics.values.Value getValue() {
		// Control tokens do not have values.

		return null;
	} // getValue

} // ControlToken
