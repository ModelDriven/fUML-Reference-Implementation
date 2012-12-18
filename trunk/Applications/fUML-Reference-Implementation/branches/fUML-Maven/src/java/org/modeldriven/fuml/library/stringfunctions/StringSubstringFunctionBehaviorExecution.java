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
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.StringValue;

public class StringSubstringFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
    	
    	StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
    	String s1 = sv1.value;
    	Debug.println("[doBody] argument, string = " + s1);
    	IntegerValue lowerValue = (IntegerValue) inputParameters.getValue(1).values.getValue(0);
    	int lower = lowerValue.value; // lower value
    	Debug.println("[doBody] argument, lower = " + lower);
    	IntegerValue upperValue = (IntegerValue) inputParameters.getValue(2).values.getValue(0);
    	int upper = upperValue.value;	// upper value
    	Debug.println("[doBody] argument, upper = " + upper);
    	
    	// Check for invalid values.  A lower value of less than 1 or greater than the
    	// length of the string is invalid.
    	if (lower < 1 || lower > s1.length()) {
    		Debug.println("[doBody] invalid lower value for String Substring: " + lower);
    		// return empty list for invalid input
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	// Same checks for upper value
    	if (upper < 1 || upper > s1.length()) {
    		Debug.println("[doBody] invalid upper value for String Substring: " + upper);
    		// return empty list for invalid input
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);   
    		return;    		
    	}
    	
    	// Upper cannot be less than lower.  Note upper and lower can be equal.
    	if (upper < lower) {
    		Debug.println("[doBody] upper is less than lower for String Substring");
    		// return empty list for invalid input
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters); 
    		return;  	
    	}
    	    	
    	// Extract the substring.  The fUML behavior differs from the Java method behavior.
    	// The fUML substring's lower value is 1-based, while the Java substring method's
    	// lower value is 0-based.  Moreover, the fUML substring's upper value includes
    	// the substring character, while the Java substring's upper value does not include
    	// the character.
    	//
    	// For example, given string "abcdefg":
    	//     In fUML, substring 2,4 equals "bcd"
    	//	   In Java, substring 2,4 equals "cd"
    	String resultString = s1.substring(lower-1, upper);
    	
    	StringValue result = new StringValue();
    	result.value = resultString;
    	result.type = this.locus.factory.getBuiltInType("String");

        Debug.println("[doBody] String Substring result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new StringSubstringFunctionBehaviorExecution();
    }   

} // StringSubstringFunctionBehaviorExecution
