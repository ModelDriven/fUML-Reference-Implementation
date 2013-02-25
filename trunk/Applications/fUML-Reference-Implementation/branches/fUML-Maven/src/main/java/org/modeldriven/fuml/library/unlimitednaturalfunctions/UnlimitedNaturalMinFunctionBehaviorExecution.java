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
 * <em><b>org::modeldriven::fuml::library::integerfunctions::UnlimitedNaturalMinFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link UnlimitedNaturalMinFunctionBehaviorExecution#doUnlimitedNaturalFunction <em>
 * doIntegerFunction</em>}</li>
 * <li>{@link UnlimitedNaturalMinFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class UnlimitedNaturalMinFunctionBehaviorExecution extends
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

    	// If a value is "unbounded", specified by -1, then is the highest possible value
    	// and cannot be the min result.  If both are unbounded, then they are the same,
    	// and either can be returned.
    	
    	if (un1.naturalValue == -1 && un2.naturalValue == -1) {
			// this case is really only to have a unique log message for unbounded,
			// since the logic is covered by the next case
			unv.value = un1;
	    	Debug.println("[doBody] Unlimited Natural Min result = unbounded(-1)");
    	} else {
        	if (un1.naturalValue == -1) {
        		unv.value = un2;
        	} else if (un2.naturalValue == -1) {
        		unv.value = un1;
        	} else {
    	    	// Perform Min function on non-unbounded values
    	    	if (un1.naturalValue <= un2.naturalValue) {
    	    		unv.value = un1;
    	    	} else {
    	    		unv.value = un2;
    	    	}
        	}	
	    	Debug.println("[doBody] Unlimited Natural Min result = " + unv.value.naturalValue);
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
        return new UnlimitedNaturalMinFunctionBehaviorExecution();
    }

} // UnlimitedNaturalMinFunctionBehaviorExecution
