
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

public abstract class PrimitiveValue extends
		fuml.semantics.classification.Value {

	public fuml.syntax.simpleclassifiers.PrimitiveType type = null;

	public fuml.semantics.classification.Value copy() {
		// Create a new value that is equal to this primitive value.

		PrimitiveValue newValue = (PrimitiveValue) (super.copy());

		newValue.type = this.type;
		return newValue;
	} // copy

	public fuml.syntax.classification.ClassifierList getTypes() {
		// Return the single primitive type of this value.

		ClassifierList types = new ClassifierList();
		types.addValue(this.type);
		return types;
	} // getTypes

} // PrimitiveValue
