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


package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.Value;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * 
 * <em><b>org::modeldriven::fuml::library::unlimitednaturalfunctions::UnlimitedNaturalGreaterThanFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link UnlimitedNaturalGreaterThanFunctionBehaviorExecution#doUnlimitedNaturalFunction <em>
 * doIntegerFunction</em>}</li>
 * <li>{@link UnlimitedNaturalGreaterThanFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class UnlimitedNaturalGreaterThanFunctionBehaviorExecution extends
        org.modeldriven.fuml.library.unlimitednaturalfunctions.UnlimitedNaturalFunctionBehaviorExecution {

    // Attributes

    // Operations of the class
    /**
     * operation doUnlimitedNaturalFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Value doUnlimitedNaturalFunction(UnlimitedNatural un1, UnlimitedNatural un2) {
    	
    	BooleanValue bv = new BooleanValue();
    	
    	// This function returns true if un1 > un2.  Value of -1 means "unbounded", which
    	// is the highest possible value.
    	
    	if (un1.naturalValue == -1 && un2.naturalValue == -1) {
    		// Both are the same value, so greaterThan function is false
    		bv.value = false;
    	} else if (un2.naturalValue == -1) {
    		// Second argument is highest possible value, so greaterThan function is false
    		bv.value = false;
    	} else if (un1.naturalValue == -1) {
    		// First argument is highest possible value, so greaterThan function is true
    		bv.value = true;
    	} else {
        	// Neither argument is highest possible value, so perform regular greaterThan function
        	if (un1.naturalValue > un2.naturalValue) {
        		bv.value = true;
        	} else {
        		bv.value = false;
        	}		    		
    	}
    	
    	Debug.println("[doBody] Unlimited Natural Greater Than results = " + bv.value);
    	return bv;
    }

    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new UnlimitedNaturalGreaterThanFunctionBehaviorExecution();
    }

} // UnlimitedNaturalGreaterThanFunctionBehaviorExecution
