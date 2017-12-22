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

import fuml.Debug;

public class IntegerLessThanEqualFunctionBehaviorExecution extends
        IntegerRelationalFunctionBehaviorExecution {

    public boolean doIntegerFunction(UMLPrimitiveTypes.intList arguments) {

    	int i1 = arguments.getValue(0);
    	int i2 = arguments.getValue(1);
    	
    	boolean b = (i1 <= i2);
    	
    	Debug.println("[doBody] Integer Less Than or Equal result = " + b);
    	return b;
    }

    public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerLessThanEqualFunctionBehaviorExecution();
    }

} // IntegerLessThanEqualFunctionBehaviorExecution
