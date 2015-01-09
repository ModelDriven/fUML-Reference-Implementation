/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009=2012 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
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
import fUML.Semantics.Classes.Kernel.IntegerValue;

public abstract class IntegerFunctionBehaviorExecution extends
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
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

        // Call the method specific to the integer function
        Integer value = this.doIntegerFunction(integerArguments);
        
        if (value == null) {
        	// if null, then there is an invalid input argument, so return
        	// an empty list
        	LibraryFunctions.addEmptyValueListToOutputList(outputParameters);     
        } else {
    		// Add output to the outputParameters list
        	IntegerValue result = new IntegerValue();
        	result.value = value;
        	result.type = this.locus.factory.getBuiltInType("Integer");
    		LibraryFunctions.addValueToOutputList(result, outputParameters);
        }             
    }

    public abstract Integer doIntegerFunction(UMLPrimitiveTypes.intList arguments);
    
} // IntegerFunctionBehaviorExecution
