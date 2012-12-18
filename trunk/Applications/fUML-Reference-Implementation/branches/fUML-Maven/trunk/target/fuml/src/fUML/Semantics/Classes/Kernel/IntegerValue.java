
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

package fUML.Semantics.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.*;
import fUML.Semantics.Loci.*;

public class IntegerValue extends fUML.Semantics.Classes.Kernel.PrimitiveValue {

	public int value = 0;

	public fUML.Syntax.Classes.Kernel.ValueSpecification specify() {
		// Return a literal integer with the value of this integer value.

		LiteralInteger literal = new LiteralInteger();

		literal.type = this.type;
		literal.value = this.value;

		return literal;
	} // specify

	public boolean equals(fUML.Semantics.Classes.Kernel.Value otherValue) {
		// Test if this integer value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this integer
		// value.

		boolean isEqual = false;
		if (otherValue instanceof IntegerValue) {
			isEqual = ((IntegerValue) otherValue).value == this.value;
		}

		return isEqual;
	} // equals

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new integer value with the same value as this integer value.

		IntegerValue newValue = (IntegerValue) (super.copy());

		newValue.value = this.value;
		return newValue;
	} // copy

	protected fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new integer value with no value.

		return new IntegerValue();
	} // new_

	public String toString() {
		String stringValue = "";

		if (this.value == 0) {
			stringValue = "0";
		} else {
			int positiveValue = this.value;

			if (positiveValue < 0) {
				positiveValue = -positiveValue;
			}

			do {
				int digit = positiveValue % 10;

				if (digit == 0) {
					stringValue = "0" + stringValue;
				} else if (digit == 1) {
					stringValue = "1" + stringValue;
				} else if (digit == 2) {
					stringValue = "2" + stringValue;
				} else if (digit == 3) {
					stringValue = "3" + stringValue;
				} else if (digit == 4) {
					stringValue = "4" + stringValue;
				} else if (digit == 5) {
					stringValue = "5" + stringValue;
				} else if (digit == 6) {
					stringValue = "6" + stringValue;
				} else if (digit == 7) {
					stringValue = "7" + stringValue;
				} else if (digit == 8) {
					stringValue = "8" + stringValue;
				} else if (digit == 9) {
					stringValue = "9" + stringValue;
				}

				positiveValue = positiveValue / 10;
			} while (positiveValue > 0);

			if (this.value < 0) {
				stringValue = "-" + stringValue;
			}
		}

		return stringValue;
	} // toString

} // IntegerValue
