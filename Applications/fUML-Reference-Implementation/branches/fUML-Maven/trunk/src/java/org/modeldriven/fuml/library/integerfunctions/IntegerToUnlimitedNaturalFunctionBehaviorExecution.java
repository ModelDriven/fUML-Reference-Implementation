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

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public class IntegerToUnlimitedNaturalFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
        // Extract integer arguments and perform an integer function on them.
    	
    	IntegerValue iv = (IntegerValue) inputParameters.getValue(0).values.getValue(0);
    	int value = iv.value;
        Debug.println("[doBody] argument = " + value);
    	
    	if (value < 0) {    		    		
    		Debug.println("[doBody] Value is <0 and cannot be converted to UnlimitedNatural: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	// Convert int to UnlimitedNatural
    	UnlimitedNatural unlimitedNatural = new UnlimitedNatural();
    	unlimitedNatural.naturalValue = value;    	
    	UnlimitedNaturalValue result = new UnlimitedNaturalValue();
    	result.value = unlimitedNatural;
    	result.type = this.locus.factory.getBuiltInType("UnlimitedNatural");

        Debug.println("[doBody] Integer ToUnlimitedNatural result = " + result.value.naturalValue);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerToUnlimitedNaturalFunctionBehaviorExecution();
    }   

} // IntegerToUnlimitedNaturalFunctionBehaviorExecution
