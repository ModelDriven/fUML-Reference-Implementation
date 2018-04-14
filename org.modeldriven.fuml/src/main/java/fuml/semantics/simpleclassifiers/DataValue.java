
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

public class DataValue extends fuml.semantics.simpleclassifiers.CompoundValue {

	public fuml.syntax.simpleclassifiers.DataType type = null;

	public fuml.syntax.classification.ClassifierList getTypes() {
		// Return the single type of this data value.

		ClassifierList types = new ClassifierList();
		types.addValue(this.type);

		return types;
	} // getTypes

	public fuml.semantics.values.Value copy() {
		// Create a new data value with the same type and feature values as this
		// data value.

		DataValue newValue = (DataValue) (super.copy());

		newValue.type = this.type;

		return newValue;
	} // copy

	protected fuml.semantics.values.Value new_() {
		// Create a new data value with no type or feature values.

		return new DataValue();
	} // new_

} // DataValue
