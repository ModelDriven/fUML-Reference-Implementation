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
package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.StringValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::booleanfunctions::BooleanToBooleanFunctionBehaviorExecution</b></em> '.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link BooleanToBooleanFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link BooleanToBooleanFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class BooleanToBooleanFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	// Attributes

	// Operations of the class
	/**
	 * operation doBody <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		// Verify that there is only one input arg
		if (inputParameters.size() != 1) {
			Debug.println("[doBody] ERROR: expected 1 input args, received "
					+ inputParameters.size());
			throw new IllegalArgumentException("invalid number of arguments");
		}

		// Extract single String input argument
		StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
		String s1 = sv1.value;	
		Debug.println("[doBody] argument = " + s1);

		// Calculate boolean value from String.  Don't use Boolean.parseBoolean()
		// since it doesn't differentiate between invalid string and false.
		boolean result = true;
		if (s1.toLowerCase().equals("true")) {
			result = true;
		} else if (s1.toLowerCase().equals("false")) {
			result = false;
		} else {
			Debug.println("[doBody] Invalid input, cannot convert to boolean: " + s1);
			// Invalid input, return empty list
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			return;
		}
		
		// Store result in BooleanValue object
		BooleanValue resultObj = new BooleanValue();
		resultObj.value = result;
		Debug.println("[doBody] Boolean ToBoolean result = " + resultObj.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}

    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new BooleanToBooleanFunctionBehaviorExecution();
    }	
	
} // BooleanToBooleanFunctionBehaviorExecution
