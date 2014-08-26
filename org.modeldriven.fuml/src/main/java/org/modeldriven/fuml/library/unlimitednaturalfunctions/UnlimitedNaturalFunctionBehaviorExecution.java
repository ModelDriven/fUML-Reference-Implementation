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
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import fUML.Semantics.Classes.Kernel.Value;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::unlimitednaturalfunctions::UnlimitedNaturalFunctionBehaviorExecution</b></em> '.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link UnlimitedNaturalFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link UnlimitedNaturalFunctionBehaviorExecution#doUnlimitedNaturalFunction <em>
 * doUnlimitedNaturalFunction</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

/**
 * Abstract class for all two-argument UnlimitedNatural functions.  For 
 * functions that have one or more than two arguments, this abstract 
 * class is not used.
 * 
 * The class which implements the specific UnlimitedNatural function must 
 * implement the doUnlimitedNaturalFunction() method.
 */
public abstract class UnlimitedNaturalFunctionBehaviorExecution extends
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

		// Get first UnlimitedNatural input argument
		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.getValue(0).values.getValue(0);
		UnlimitedNatural un1 = unv1.value;
		if (un1.naturalValue == -1) {
			Debug.println("[doBody] argument = unbounded(-1)");
		} else {
			Debug.println("[doBody] argument = " + un1.naturalValue);
		}
		
		// Verify that the UnlimitedNatural cannot be less than -1.  Recall that
		// -1 specifies the "unbounded" value.
		if (un1.naturalValue < -1) {
			Debug.println("[doBody] invalid UnlimitedNatural value (throwing exception) = " + un1.naturalValue);
			throw new IllegalArgumentException("UnlimitedNatural cannot be less than -1, where -1 specifies unbounded");
		}
		
		// Get second UnlimitedNatural input argument
		UnlimitedNaturalValue unv2 = (UnlimitedNaturalValue) inputParameters.getValue(1).values.getValue(0);
		UnlimitedNatural un2 = unv2.value;
		if (un2.naturalValue == -1) {
			Debug.println("[doBody] argument = unbounded(-1)");
		} else {
			Debug.println("[doBody] argument = " + un2.naturalValue);
		}
		// Verify that the UnlimitedNatural cannot be less than -1.  Recall that
		// -1 specifies the "unbounded" value.		
		if (un2.naturalValue < -1) {
			Debug.println("[doBody] invalid UnlimitedNatural value (throwing exception) = " + un1.naturalValue);
			throw new IllegalArgumentException("UnlimitedNatural cannot be less than -1, where -1 specifies unbounded");
		}
		
		// Call method on concrete implementation subclass
		Value resultObj = doUnlimitedNaturalFunction(un1, un2);
		
		// A result of null indicates an invalid input which causes an empty output
		if (resultObj == null) {
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);	
		}		
	}
	
    /**
     * operation doUnlimitedNaturalFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public abstract Value doUnlimitedNaturalFunction(UnlimitedNatural un1, UnlimitedNatural un2);	

} // UnlimitedNaturalFunctionBehaviorExecution
