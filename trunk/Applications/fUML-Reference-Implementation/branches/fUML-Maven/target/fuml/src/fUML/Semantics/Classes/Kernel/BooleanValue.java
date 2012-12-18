
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

public class BooleanValue extends fUML.Semantics.Classes.Kernel.PrimitiveValue {

	public boolean value = false;

	public fUML.Syntax.Classes.Kernel.ValueSpecification specify() {
		// Return a literal boolean with the value of this boolean value.

		LiteralBoolean literal = new LiteralBoolean();

		literal.type = this.type;
		literal.value = this.value;

		return literal;
	} // specify

	public boolean equals(fUML.Semantics.Classes.Kernel.Value otherValue) {
		// Test if this boolean value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this boolean
		// value.

		boolean isEqual = false;
		if (otherValue instanceof BooleanValue) {
			isEqual = ((BooleanValue) otherValue).value == this.value;
		}

		return isEqual;
	} // equals

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new boolean value with the same value as this boolean value.

		BooleanValue newValue = (BooleanValue) (super.copy());

		newValue.value = this.value;
		return newValue;
	} // copy

	protected fUML.Semantics.Classes.Kernel.Value new_() {
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
