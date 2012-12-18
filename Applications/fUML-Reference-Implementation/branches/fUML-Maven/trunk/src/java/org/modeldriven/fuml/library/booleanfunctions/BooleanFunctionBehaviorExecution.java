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

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;

public abstract class BooleanFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

        List<Boolean> arguments = new ArrayList<Boolean>();
        
        for (int i = 0; i < inputParameters.size(); i++) {
            boolean value = ((BooleanValue) (inputParameters.getValue(i)).values.getValue(0)).value;
            Debug.println("[doBody] argument = " + value);
            arguments.add(value);
        }

		boolean value = doBooleanFunction(arguments);

		BooleanValue result = new BooleanValue();
		result.value = value;
		result.type = this.locus.factory.getBuiltInType("Boolean");

		LibraryFunctions.addValueToOutputList(result, outputParameters);
	}
	
    public abstract boolean doBooleanFunction(List<Boolean> arguments);

} // BooleanFunctionBehaviorExecution
