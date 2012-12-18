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

package org.modeldriven.fuml.library.realfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.RealValue;

public class RealRoundFunctionBehaviorExecution extends
fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
    	
    	RealValue rv = (RealValue) inputParameters.getValue(0).values.getValue(0);
    	float x = rv.value;
        Debug.println("[doBody] argument = " + x);
    	
        int resultInt;
        try {
        	resultInt = (int)Math.round(x);
        } catch (Exception e) {
        	LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
        	return;
        }
        
    	IntegerValue result = new IntegerValue();
    	result.value = resultInt;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] Real Round result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new RealRoundFunctionBehaviorExecution();
    }

} 
