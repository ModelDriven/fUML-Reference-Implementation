
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

import fuml.syntax.classification.ClassifierList;
import fuml.syntax.classification.InstanceSpecification;
import fuml.syntax.classification.InstanceValue;

public class EnumerationValue extends fuml.semantics.classification.Value {

	public fuml.syntax.simpleclassifiers.EnumerationLiteral literal = null;
	public fuml.syntax.simpleclassifiers.Enumeration type = null;

	public fuml.syntax.values.ValueSpecification specify() {
		// Return an instance value with literal as the instance.

		InstanceValue instanceValue = new InstanceValue();
		InstanceSpecification instance = new InstanceSpecification();

		instanceValue.type = this.type;
		instanceValue.instance = this.literal;

		return instanceValue;
	} // specify

	public boolean equals(fuml.semantics.classification.Value otherValue) {
		// Test if this enumeration value is equal to the otherValue.
		// To be equal, the otherValue must also be an enumeration value with
		// the same literal as this enumeration value.

		boolean isEqual = false;
		if (otherValue instanceof EnumerationValue) {
			isEqual = ((EnumerationValue) otherValue).literal == this.literal;
		}

		return isEqual;
	} // equals

	public fuml.semantics.classification.Value copy() {
		// Create a new enumeration value with the same literal as this
		// enumeration value.

		EnumerationValue newValue = (EnumerationValue) (super.copy());

		newValue.type = this.type;
		newValue.literal = this.literal;

		return newValue;
	} // copy

	protected fuml.semantics.classification.Value new_() {
		// Create a new enumeration value with no literal.

		return new EnumerationValue();
	} // new_

	public fuml.syntax.classification.ClassifierList getTypes() {
		// Return the single type of this enumeration value.

		ClassifierList types = new ClassifierList();
		types.addValue(this.type);

		return types;
	} // getTypes

	public String toString() {
		return literal.name;
	} // toString

} // EnumerationValue
