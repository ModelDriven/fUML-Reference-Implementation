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

package org.modeldriven.fuml.library.listfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fuml.Debug;
import fuml.semantics.classification.ValueList;
import fuml.syntax.simpleclassifiers.IntegerValue;

public class ListSizeFunctionBehaviorExecution extends
        fuml.semantics.commonbehavior.OpaqueBehaviorExecution {

	@Override
    public void doBody(
            fuml.semantics.commonbehavior.ParameterValueList inputParameters,
            fuml.semantics.commonbehavior.ParameterValueList outputParameters) {
    	
    	// Get the list for which to determine the size
    	ValueList vl = (ValueList) inputParameters.getValue(0).values;
    	
    	// Determine the length of the list
    	int size = vl.size();
    	
    	// Return the size in an IntegerValue object
    	IntegerValue result = new IntegerValue();
    	result.value = size;
    	result.type = this.locus.factory.getBuiltInType("Integer");

        Debug.println("[doBody] List Size, result=" + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
	@Override
    public fuml.semantics.classification.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new ListSizeFunctionBehaviorExecution();
    }   

} // ListSizeFunctionBehaviorExecution
