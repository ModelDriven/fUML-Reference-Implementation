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

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.StringValue;

public class StringConcatFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
    	
    	StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String s1 = sv1.value;
        Debug.println("[doBody] argument = " + s1);

    	StringValue sv2 = (StringValue) inputParameters.getValue(1).values.getValue(0);
    	String s2 = sv2.value;
        Debug.println("[doBody] argument = " + s2);
    	
    	// Concatenate the two strings
    	String resultString = s1 + s2;
    	
    	StringValue result = new StringValue();
    	result.value = resultString;
    	result.type = this.locus.factory.getBuiltInType("String");

        Debug.println("[doBody] String Concat result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
	@Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new StringConcatFunctionBehaviorExecution();
    }   

} // StringConcatFunctionBehaviorExecution
