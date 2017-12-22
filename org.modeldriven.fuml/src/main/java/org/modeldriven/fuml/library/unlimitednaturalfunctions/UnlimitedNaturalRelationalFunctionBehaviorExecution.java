/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.intList;
import fuml.Debug;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;

public abstract class UnlimitedNaturalRelationalFunctionBehaviorExecution extends
        fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

    public void doBody(
            fuml.semantics.commonbehavior.ParameterValueList inputParameters,
            fuml.semantics.commonbehavior.ParameterValueList outputParameters) {
        // Extract unlimited natural arguments and perform an integer function on them.

        intList integerArguments = new intList();

        for (int i = 0; i < inputParameters.size(); i++) {
            int value = ((UnlimitedNaturalValue) (inputParameters.getValue(i)).values.getValue(0)).value.naturalValue;
            Debug.println("[doBody] argument = " + value);
            integerArguments.addValue(value);
        }

        BooleanValue result = new BooleanValue();
        result.value = this.doUnlimitedNaturalFunction(integerArguments);
        result.type = this.locus.factory.getBuiltInType("Boolean");

        Debug.println("[doBody] result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }

    public abstract boolean doUnlimitedNaturalFunction(intList arguments);
} // IntegerFunctionBehaviorExecution_ReturnBoolean
