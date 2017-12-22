/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.integerfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fuml.Debug;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.syntax.simpleclassifiers.IntegerValue;

public class IntegerDivideFunctionBehaviorExecution extends
		fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fuml.semantics.commonbehavior.ParameterValueList inputParameters,
			fuml.semantics.commonbehavior.ParameterValueList outputParameters) {
		// Extract integer arguments and perform an integer function on them.

		int arg1 = ((IntegerValue) (inputParameters.getValue(0)).values
				.getValue(0)).value;
		int arg2 = ((IntegerValue) (inputParameters.getValue(1)).values
				.getValue(0)).value;

		// Check for illegal divide by zero
		if (arg2 == 0) {
			Debug.println("[doBody] Integer Divide, divide by zero not allowed");
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		}

		// Perform Divide function
		float x = ((float) arg1) / ((float) arg2);
		Debug.println("[doBody] Integer Divide result = " + x);

		RealValue result = new RealValue();
		result.value = x;
		result.type = this.locus.factory.getBuiltInType("Real");
		LibraryFunctions.addValueToOutputList(result, outputParameters);
	}

	public fuml.semantics.classification.Value new_() {
		// Create a new instance of this kind of function behavior execution.

		return new IntegerDivideFunctionBehaviorExecution();
	}

} // IntegerDivideFunctionBehaviorExecution
