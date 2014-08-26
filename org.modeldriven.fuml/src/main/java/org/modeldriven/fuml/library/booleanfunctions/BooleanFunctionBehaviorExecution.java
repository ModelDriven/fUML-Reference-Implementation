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

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::booleanfunctions::BooleanFunctionBehaviorExecution</b></em> '.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link BooleanFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link BooleanFunctionBehaviorExecution#doBooleanFunction <em>
 * doBooleanFunction</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

/**
 * Abstract class for all two-argument boolean functions.  For functions
 * that have one or more than two arguments, this abstract class is not
 * used.
 * 
 * The class which implements the specific boolean function must implement
 * the doBooleanFunction() method.
 */
public abstract class BooleanFunctionBehaviorExecution extends
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

		// Verify that there are only two input args
		if (inputParameters.size() != 2) {
			Debug.println("[doBody] ERROR: expected 2 input args, received "
					+ inputParameters.size());
			throw new IllegalArgumentException("invalid number of arguments");
		}

		// Get first boolean input argument
		BooleanValue bv1 = (BooleanValue) inputParameters.getValue(0).values.getValue(0);
		boolean b1 = bv1.value;
		Debug.println("[doBody] argument = " + b1);
		
		// Get second boolean input argument
		BooleanValue bv2 = (BooleanValue) inputParameters.getValue(1).values.getValue(0);
		boolean b2 = bv2.value;
		Debug.println("[doBody] argument = " + b2);

		// Call method on concrete implementation subclass
		boolean result = doBooleanFunction(b1, b2);

		// Store result in BooleanValue object
		BooleanValue resultObj = new BooleanValue();
		resultObj.value = result;

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}
	
    /**
     * operation doBooleanFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public abstract boolean doBooleanFunction(boolean b1, boolean b2);	

} // BooleanFunctionBehaviorExecution
