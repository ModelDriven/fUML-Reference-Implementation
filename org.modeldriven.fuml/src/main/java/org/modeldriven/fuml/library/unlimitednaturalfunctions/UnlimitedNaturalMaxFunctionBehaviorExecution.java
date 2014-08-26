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
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import fUML.Semantics.Classes.Kernel.Value;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * 
 * <em><b>org::modeldriven::fuml::library::integerfunctions::UnlimitedNaturalMaxFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link UnlimitedNaturalMaxFunctionBehaviorExecution#doUnlimitedNaturalFunction <em>
 * doIntegerFunction</em>}</li>
 * <li>{@link UnlimitedNaturalMaxFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class UnlimitedNaturalMaxFunctionBehaviorExecution extends
        org.modeldriven.fuml.library.unlimitednaturalfunctions.UnlimitedNaturalFunctionBehaviorExecution {

    // Attributes

    // Operations of the class
    /**
     * operation doUnlimitedNaturalFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Value doUnlimitedNaturalFunction(UnlimitedNatural un1, UnlimitedNatural un2) {
    	    	
    	UnlimitedNaturalValue unv = new UnlimitedNaturalValue();
    	
    	// First check for special cases of "unbounded", specified by -1 value, which
    	// indicates the max value
    	if (un1.naturalValue == -1) {
    		unv.value = un1;
        	Debug.println("[doBody] Unlimited Natural Max result = unbounded(-1)");
    	} else if (un2.naturalValue == -1) {
    		unv.value = un2;
        	Debug.println("[doBody] Unlimited Natural Max result = unbounded(-1)");
    	} else if (un1.naturalValue == -1 && un2.naturalValue == -1) {
    		// this case is really only to have a unique log message for unbounded,
    		// since the logic is covered by the next case
    		unv.value = un1;
        	Debug.println("[doBody] Unlimited Natural Max result = unbounded(-1)");
    	} else {    	
	    	// Now perform basic max function
	    	if (un1.naturalValue >= un2.naturalValue) {
	    		unv.value = un1;
	    	} else {
	    		unv.value = un2;
	    	}
	    	Debug.println("[doBody] Unlimited Natural Max result = " + unv.value.naturalValue);
    	}
    	
    	return unv;
    }

    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new UnlimitedNaturalMaxFunctionBehaviorExecution();
    }

} // UnlimitedNaturalMaxFunctionBehaviorExecution
