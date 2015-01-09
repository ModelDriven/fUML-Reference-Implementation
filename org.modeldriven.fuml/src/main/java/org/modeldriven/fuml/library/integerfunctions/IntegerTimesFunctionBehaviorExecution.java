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

public class IntegerTimesFunctionBehaviorExecution extends
        IntegerFunctionBehaviorExecution {

    public Integer doIntegerFunction(UMLPrimitiveTypes.intList arguments) {

    	int i1 = arguments.getValue(0);
    	int i2 = arguments.getValue(1);
    	
        // Compute the integer times function.
        int i = i1 * i2;
        
    	Debug.println("[doBody] Integer Multiply result = " + i);    	
    	return i;
    }

    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerTimesFunctionBehaviorExecution();
    }

} // IntegerTimesFunctionBehaviorExecution
