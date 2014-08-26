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

import UMLPrimitiveTypes.intList;
import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::integerfunctions::IntegerFunctionBehaviorExecution_ReturnBoolean</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link IntegerFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * <li>{@link IntegerFunctionBehaviorExecution#doIntegerFunction <em>
 * doIntegerFunction</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public abstract class IntegerFunctionBehaviorExecution_ReturnBoolean extends
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

        intList integerArguments = new intList();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((IntegerValue) (inputParameters.getValue(i)).values.getValue(0)).value;
            Debug.println("[doBody] argument = " + value);
            integerArguments.addValue(value);
        }

        BooleanValue result = new BooleanValue();
        result.value = this.doIntegerFunction(integerArguments);

        Debug.println("[doBody] result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    /**
     * operation doIntegerFunction <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public abstract boolean doIntegerFunction(UMLPrimitiveTypes.intList arguments);
} // IntegerFunctionBehaviorExecution_ReturnBoolean
