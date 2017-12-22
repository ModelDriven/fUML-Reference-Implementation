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
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.syntax.simpleclassifiers.IntegerValue;

public class IntegerToStringFunctionBehaviorExecution extends
        fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

    public void doBody(
            fuml.semantics.commonbehavior.ParameterValueList inputParameters,
            fuml.semantics.commonbehavior.ParameterValueList outputParameters) {
    	
    	IntegerValue iv = (IntegerValue) inputParameters.getValue(0).values.getValue(0);
    	int i = iv.value;
        Debug.println("[doBody] argument = " + i);
    	
    	// Convert int to String
    	String resultString = Integer.toString(i);    	
    	StringValue result = new StringValue();
    	result.value = resultString;
    	result.type = this.locus.factory.getBuiltInType("String");

        Debug.println("[doBody] Integer ToString result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerToStringFunctionBehaviorExecution();
    }   

} // IntegerToStringFunctionBehaviorExecution
