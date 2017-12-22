
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

package fuml.semantics.simpleclassifiers;

import fuml.syntax.values.LiteralBoolean;

public class BooleanValue extends fuml.semantics.simpleclassifiers.PrimitiveValue {

	public boolean value = false;

	public fuml.syntax.values.ValueSpecification specify() {
		// Return a literal boolean with the value of this boolean value.

		LiteralBoolean literal = new LiteralBoolean();

		literal.type = this.type;
		literal.value = this.value;

		return literal;
	} // specify

	public boolean equals(fuml.semantics.classification.Value otherValue) {
		// Test if this boolean value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this boolean
		// value.

		boolean isEqual = false;
		if (otherValue instanceof BooleanValue) {
			isEqual = ((BooleanValue) otherValue).value == this.value;
		}

		return isEqual;
	} // equals

	public fuml.semantics.classification.Value copy() {
		// Create a new boolean value with the same value as this boolean value.

		BooleanValue newValue = (BooleanValue) (super.copy());

		newValue.value = this.value;
		return newValue;
	} // copy

	protected fuml.semantics.classification.Value new_() {
		// Return a new boolean value with no value.

		return new BooleanValue();
	} // new_

	public String toString() {
		String stringValue = "false";

		if (this.value) {
			stringValue = "true";
		}

		return stringValue;

	} // toString

} // BooleanValue
