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

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::unlimitednaturalfunctions::UnlimitedNaturalToIntegerFunctionBehaviorExecution</b></em> '.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link UnlimitedNaturalToIntegerFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link UnlimitedNaturalToIntegerFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class UnlimitedNaturalToIntegerFunctionBehaviorExecution extends
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

		// Verify that there is only input arg
		if (inputParameters.size() != 1) {
			Debug.println("[doBody] ERROR: expected 1 input arg, received "
					+ inputParameters.size());
			throw new IllegalArgumentException("invalid number of arguments");
		}

		// Get first UnlimitedNatural input argument
		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.getValue(0).values.getValue(0);
		UnlimitedNatural un1 = unv1.value;
		Debug.println("[doBody] argument = " + un1.naturalValue);
		
		// Verify that the UnlimitedNatural cannot be less than -1.  Recall that
		// -1 specifies the "unbounded" value.
		if (un1.naturalValue < -1) {
			Debug.println("[doBody] UnlimitedNatural value invalid (throwing exception): " + un1.naturalValue);
			throw new IllegalArgumentException("UnlimitedNatural cannot be less than 0");
		}
		
		// If the unlimited natural is unbounded (equal to -1), it cannot be
		// converted to an integer.  Return an empty values list.
		if (un1.naturalValue == -1) {
			Debug.println("[doBody] Unbounded input invalid for ToInteger function");
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			IntegerValue resultObj = new IntegerValue();
			resultObj.value = un1.naturalValue;
			Debug.println("[doBody] Unlimited Natural ToInteger result = " + resultObj.value);
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
		}
	}
	
    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new UnlimitedNaturalToIntegerFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToIntegerFunctionBehaviorExecution
