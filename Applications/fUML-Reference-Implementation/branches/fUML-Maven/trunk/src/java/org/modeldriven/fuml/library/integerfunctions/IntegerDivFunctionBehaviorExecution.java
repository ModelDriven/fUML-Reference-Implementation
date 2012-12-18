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

import fUML.Debug;

public class IntegerDivFunctionBehaviorExecution extends
        org.modeldriven.fuml.library.integerfunctions.IntegerFunctionBehaviorExecution {

    public Integer doIntegerFunction(UMLPrimitiveTypes.intList arguments) {
        // Compute the integer divide function.
    	
    	int arg1 = arguments.getValue(0);
    	int arg2 = arguments.getValue(1);

    	// Check for illegal divide by zero
    	if (arg2 == 0) {
        	Debug.println("[doBody] Integer Div, divide by zero not allowed");    		
    		return null;
    	}

    	// Perform Divide function
    	int i = arg1/arg2;
    	Debug.println("[doBody] Integer Div result = " + i);
    	return i;
    }

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.

        return new IntegerDivFunctionBehaviorExecution();
    }

} // IntegerDivFunctionBehaviorExecution
