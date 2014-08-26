
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

package fUML.Semantics.CommonBehaviors.BasicBehaviors;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.Loci.*;

public class ParameterValue extends org.modeldriven.fuml.FumlObject {

	public fUML.Syntax.Classes.Kernel.Parameter parameter = null;
	public fUML.Semantics.Classes.Kernel.ValueList values = new fUML.Semantics.Classes.Kernel.ValueList();

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue copy() {
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
