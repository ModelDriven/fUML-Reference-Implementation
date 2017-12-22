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

package org.modeldriven.fuml.library.stringfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fuml.Debug;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.syntax.simpleclassifiers.IntegerValue;

public class StringSizeFunctionBehaviorExecution extends
        fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

	@Override
    public void doBody(
            fuml.semantics.commonbehavior.ParameterValueList inputParameters,
            fuml.semantics.commonbehavior.ParameterValueList outputParameters) {
    	
    	StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String s1 = sv1.value;
        Debug.println("[doBody] argument = " + s1);
    	
    	// Determine the length of the String
    	int size = s1.length();
    	
    	IntegerValue result = new IntegerValue();
    	result.value = size;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] String Size result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
	@Override
    public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new StringSizeFunctionBehaviorExecution();
    }   

} // StringSizeFunctionBehaviorExecution
