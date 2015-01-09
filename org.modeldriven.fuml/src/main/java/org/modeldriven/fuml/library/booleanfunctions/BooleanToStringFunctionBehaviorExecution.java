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

public class BooleanToStringFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		BooleanValue bv1 = (BooleanValue) inputParameters.getValue(0).values.getValue(0);
		Debug.println("[doBody] argument = " + bv1.value);

		StringValue resultObj = new StringValue();
		resultObj.value = bv1.toString();
		resultObj.type = this.locus.factory.getBuiltInType("String");
		Debug.println("[doBody] Boolean ToString result = " + resultObj.value);
		
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}

    @Override
	public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new BooleanToStringFunctionBehaviorExecution();
    }	
	
} // BooleanToStringFunctionBehaviorExecution
