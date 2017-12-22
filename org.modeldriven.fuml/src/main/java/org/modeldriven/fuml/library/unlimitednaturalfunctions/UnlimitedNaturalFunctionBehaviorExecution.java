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

import UMLPrimitiveTypes.UnlimitedNatural;
import UMLPrimitiveTypes.intList;
import fuml.Debug;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;

public abstract class UnlimitedNaturalFunctionBehaviorExecution extends
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

        // Call the method specific to the integer function
        int value = this.doUnlimitedNaturalFunction(integerArguments);
        
        // Add output to the outputParameters list
        UnlimitedNaturalValue result = new UnlimitedNaturalValue();
        result.value = new UnlimitedNatural();
        result.value.naturalValue = value;
        result.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
        LibraryFunctions.addValueToOutputList(result, outputParameters);
	}
	
    public abstract int doUnlimitedNaturalFunction(intList arguments);	

} // UnlimitedNaturalFunctionBehaviorExecution
