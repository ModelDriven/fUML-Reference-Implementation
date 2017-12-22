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

import fuml.Debug;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.simpleclassifiers.StringValue;

public class RealToRealFunctionBehaviorExecution extends
        fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

    public void doBody(
            fuml.semantics.commonbehavior.ParameterValueList inputParameters,
            fuml.semantics.commonbehavior.ParameterValueList outputParameters) {

    	StringValue sv = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String value = sv.value;
		Debug.println("[doBody] argument = " + value);

    	float resultFloat;
    	try {
    		resultFloat = Float.parseFloat(value);
    	} catch (NumberFormatException e) {
    		Debug.println("[doBody] string does not specify a real: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	RealValue result = new RealValue();
    	result.value = resultFloat;
    	result.type = this.locus.factory.getBuiltInType("Real");

        Debug.println("[doBody] Real ToReal result = " + result.value);

		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new RealToRealFunctionBehaviorExecution();
    }   

} 
