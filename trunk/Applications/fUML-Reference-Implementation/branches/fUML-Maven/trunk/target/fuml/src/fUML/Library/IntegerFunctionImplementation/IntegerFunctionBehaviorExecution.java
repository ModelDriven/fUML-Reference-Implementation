
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

public abstract class IntegerFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
		// Extract integer arguments and perform an integer function on them.

		intList integerArguments = new intList();

		for (int i = 0; i < inputParameters.size(); i++) {
			int value = ((IntegerValue) (inputParameters.getValue(i)).values
					.getValue(0)).value;
			Debug.println("[doBody] argument = " + value);
			integerArguments.addValue(value);
		}

		IntegerValue result = new IntegerValue();
		result.value = this.doIntegerFunction(integerArguments);

		Debug.println("[doBody] result = " + result.value);

		ValueList values = new ValueList();
		values.addValue(result);
		outputParameters.getValue(0).values = values;

	} // doBody

	public abstract int doIntegerFunction(UMLPrimitiveTypes.intList arguments);

} // IntegerFunctionBehaviorExecution
