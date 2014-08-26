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

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::integerfunctions::IntegerToUnlimitedNaturalFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link IntegerToUnlimitedNaturalFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * doIntegerFunction</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IntegerToUnlimitedNaturalFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

    // Attributes

    // Operations of the class
	
    /**
     * operation doBody <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void doBody(
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
            fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
        // Extract integer arguments and perform an integer function on them.
    	
    	IntegerValue iv = (IntegerValue) inputParameters.getValue(0).values.getValue(0);
    	int value = iv.value;
        Debug.println("[doBody] argument = " + value);
    	
    	if (value < 0) {    		    		
    		Debug.println("[doBody] Value is <0 and cannot be converted to UnlimitedNatural: " + value);
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	// Convert int to UnlimitedNatural
    	UnlimitedNatural unlimitedNatural = new UnlimitedNatural();
    	unlimitedNatural.naturalValue = value;    	
    	UnlimitedNaturalValue result = new UnlimitedNaturalValue();
    	result.value = unlimitedNatural;    	

        Debug.println("[doBody] Integer ToUnlimitedNatural result = " + result.value.naturalValue);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new IntegerToUnlimitedNaturalFunctionBehaviorExecution();
    }   

} // IntegerToUnlimitedNaturalFunctionBehaviorExecution
