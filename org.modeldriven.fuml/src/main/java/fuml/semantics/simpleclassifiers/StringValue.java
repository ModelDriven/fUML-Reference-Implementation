
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

import fuml.syntax.values.LiteralString;

public class StringValue extends fuml.semantics.simpleclassifiers.PrimitiveValue {

	public String value = "";

	public fuml.syntax.values.ValueSpecification specify() {
		// Return a literal string with the value of this string value.

		LiteralString literal = new LiteralString();

		literal.type = this.type;
		literal.value = this.value;

		return literal;
	} // specify

	public boolean equals(fuml.semantics.values.Value otherValue) {
		// Test if this string value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this string
		// value.

		boolean isEqual = false;
		if (otherValue instanceof StringValue) {
			isEqual = ((StringValue) otherValue).value.equals(this.value);
		}

		return isEqual;
	} // equals

	public fuml.semantics.values.Value copy() {
		// Create a new string value with the same value as this string value.

		StringValue newValue = (StringValue) (super.copy());

		newValue.value = this.value;
		return newValue;
	} // copy

	protected fuml.semantics.values.Value new_() {
		// Create a new string value with no value.

		return new StringValue();
	} // new_

	public String toString() {
		return value;
	} // toString

} // StringValue
