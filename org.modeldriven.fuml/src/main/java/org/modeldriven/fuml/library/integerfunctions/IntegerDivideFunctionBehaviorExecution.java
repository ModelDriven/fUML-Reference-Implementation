/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */


package org.modeldriven.fuml.library.integerfunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Value;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * 
 * <em><b>org::modeldriven::fuml::library::integerfunctions::IntegerDivideFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link IntegerDivideFunctionBehaviorExecution#doIntegerFunction <em>
 * doIntegerFunction</em>}</li>
 * <li>{@link IntegerDivideFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class IntegerDivideFunctionBehaviorExecution extends
        org.modeldriven.fuml.library.integerfunctions.IntegerFunctionBehaviorExecution {

    // Attributes

    // Operations of the class
    /**
     * operation doIntegerFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Value doIntegerFunction(UMLPrimitiveTypes.intList arguments) {
        // Compute the integer divide function.
    	
    	int arg1 = arguments.getValue(0);
    	int arg2 = arguments.getValue(1);

    	// Check for illegal divide by zero
    	if (arg2 == 0) {
        	Debug.println("[doBody] Integer Divide, divide by zero not allowed");    		
    		return null;
    	}

    	// Perform Divide function
    	IntegerValue iv = new IntegerValue();
    	iv.value = arg1/arg2;
    	Debug.println("[doBody] Integer Divide result = " + iv.value);
    	return iv;
    }

    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.

        return new IntegerDivideFunctionBehaviorExecution();
    }

} // IntegerDivideFunctionBehaviorExecution
