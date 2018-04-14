
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

package fuml.semantics.commonbehavior;

import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;

public class ParameterValue extends org.modeldriven.fuml.FumlObject {

	public fuml.syntax.classification.Parameter parameter = null;
	public fuml.semantics.values.ValueList values = new fuml.semantics.values.ValueList();

	public fuml.semantics.commonbehavior.ParameterValue copy() {
		// Create a new parameter value for the same parameter as this parameter
		// value, but with copies of the values of this parameter value.

		ParameterValue newValue = new ParameterValue();

		newValue.parameter = this.parameter;

		ValueList values = this.values;
		for (int i = 0; i < values.size(); i++) {
			Value value = values.getValue(i);
			newValue.values.addValue(value.copy());
		}

		return newValue;
	} // copy

} // ParameterValue
