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

import fuml.Debug;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.StringValue;

public class BooleanToStringFunctionBehaviorExecution extends
		fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fuml.semantics.commonbehavior.ParameterValueList inputParameters,
			fuml.semantics.commonbehavior.ParameterValueList outputParameters) {

		BooleanValue bv1 = (BooleanValue) inputParameters.getValue(0).values.getValue(0);
		Debug.println("[doBody] argument = " + bv1.value);

		StringValue resultObj = new StringValue();
		resultObj.value = bv1.toString();
		resultObj.type = this.locus.factory.getBuiltInType("String");
		Debug.println("[doBody] Boolean ToString result = " + resultObj.value);
		
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}

    @Override
	public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new BooleanToStringFunctionBehaviorExecution();
    }	
	
} // BooleanToStringFunctionBehaviorExecution
