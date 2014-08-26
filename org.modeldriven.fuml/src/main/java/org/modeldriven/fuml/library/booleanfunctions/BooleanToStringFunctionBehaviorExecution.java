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
 * <em><b>org::modeldriven::fuml::library::booleanfunctions::BooleanToStringFunctionBehaviorExecution</b></em> '.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link BooleanToStringFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link BooleanToStringFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class BooleanToStringFunctionBehaviorExecution extends
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

		// Extract single boolean input argument
		BooleanValue bv1 = (BooleanValue) inputParameters.getValue(0).values.getValue(0);
		boolean b1 = bv1.value;
		Debug.println("[doBody] argument = " + b1);

		// Convert to String
		String result;
		if (b1) {
			result = "true";
		} else {
			result = "false";			
		}		

		// Store result in StringValue object
		StringValue resultObj = new StringValue();
		resultObj.value = result;
		Debug.println("[doBody] Boolean ToString result = " + resultObj.value);
		
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
        return new BooleanToStringFunctionBehaviorExecution();
    }	
	
} // BooleanToStringFunctionBehaviorExecution
