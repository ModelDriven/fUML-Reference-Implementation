
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

package fUML.Library.IntegerFunctionImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Semantics.Classes.Kernel.*;

public class IntegerGreaterFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
		int value0 = ((IntegerValue) inputParameters.getValue(0).values
				.getValue(0)).value;
		int value1 = ((IntegerValue) inputParameters.getValue(1).values
				.getValue(0)).value;

		BooleanValue result = new BooleanValue();
		result.value = value0 > value1;

		Debug.println("[doBody] result = " + result.value);

		ValueList values = new ValueList();
		values.addValue(result);
		outputParameters.getValue(0).values = values;
	} // doBody

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new instance of this kind of function behavior execution.

		return new IntegerGreaterFunctionBehaviorExecution();
	} // new_

} // IntegerGreaterFunctionBehaviorExecution
