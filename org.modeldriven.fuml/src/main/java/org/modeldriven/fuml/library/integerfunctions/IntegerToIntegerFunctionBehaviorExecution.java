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

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.StringValue;

public class IntegerToIntegerFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
        // Extract integer arguments and perform an integer function on them.

    	StringValue sv = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String value = sv.value;
		Debug.println("[doBody] argument = " + value);

    	// Convert String to int
    	int resultInt;
    	try {
    		resultInt = Integer.parseInt(value);
    	} catch (NumberFormatException e) {
    		// If the String does not specify an integer, simply return an empty values list
    		Debug.println("[doBody] string does not specify an integer: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	IntegerValue result = new IntegerValue();
    	result.value = resultInt;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] Integer ToInteger result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerToIntegerFunctionBehaviorExecution();
    }   

} // IntegerToIntegerFunctionBehaviorExecution
