
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

public class UnlimitedNaturalValue extends
		fUML.Semantics.Classes.Kernel.PrimitiveValue {

	public UnlimitedNatural value = null;

	public fUML.Syntax.Classes.Kernel.ValueSpecification specify() {
		// Return a literal unlimited natural with the value of this unlimited
		// natural value.

		LiteralUnlimitedNatural literal = new LiteralUnlimitedNatural();

		literal.type = this.type;
		literal.value = this.value;

		return literal;
	} // specify

	public boolean equals(fUML.Semantics.Classes.Kernel.Value otherValue) {
		// Test if this unlimited natural value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this
		// unlimited natural value.

		boolean isEqual = false;
		if (otherValue instanceof UnlimitedNaturalValue) {
			isEqual = ((UnlimitedNaturalValue) otherValue).value == this.value;
		}

		return isEqual;
	} // equals

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new unlimited natural value with the same value as this
		// value.

		UnlimitedNaturalValue newValue = (UnlimitedNaturalValue) (super.copy());

		newValue.value = this.value;
		return newValue;
	} // copy

	protected fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new unlimited natural value with no value.

		return new UnlimitedNaturalValue();
	} // new_

	public String toString() {
		String stringValue = "*";

		if (this.value.naturalValue >= 0) {
			IntegerValue integerValue = new IntegerValue();
			integerValue.value = this.value.naturalValue;
			stringValue = integerValue.toString();
		}

		return stringValue;

	} // toString

} // UnlimitedNaturalValue
