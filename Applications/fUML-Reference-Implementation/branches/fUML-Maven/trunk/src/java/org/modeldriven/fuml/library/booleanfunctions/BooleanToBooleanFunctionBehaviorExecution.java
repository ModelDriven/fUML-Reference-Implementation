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

package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.StringValue;

public class BooleanToBooleanFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		// Extract single String input argument
		StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
		String s1 = sv1.value;	
		Debug.println("[doBody] argument = " + s1);

		// Calculate boolean value from String.  Don't use Boolean.parseBoolean()
		// since it doesn't differentiate between invalid string and false.
		boolean result = true;
		if (s1.toLowerCase().equals("true")) {
			result = true;
		} else if (s1.toLowerCase().equals("false")) {
			result = false;
		} else {
			Debug.println("[doBody] Invalid input, cannot convert to boolean: " + s1);
			// Invalid input, return empty list
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			return;
		}
		
		// Store result in BooleanValue object
		BooleanValue resultObj = new BooleanValue();
		resultObj.value = result;
		resultObj.type = this.locus.factory.getBuiltInType("Boolean");
		Debug.println("[doBody] Boolean ToBoolean result = " + resultObj.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}

    @Override
	public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BooleanToBooleanFunctionBehaviorExecution();
    }	
	
} // BooleanToBooleanFunctionBehaviorExecution
