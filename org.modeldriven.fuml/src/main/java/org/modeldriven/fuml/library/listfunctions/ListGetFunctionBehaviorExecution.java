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


package org.modeldriven.fuml.library.listfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::listfunctions::ListGetFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ListGetFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * doIntegerFunction</em>}</li>
 * <li>{@link ListGetFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ListGetFunctionBehaviorExecution extends
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
    	
    	// Get the list for which to find the element from the first argument
    	ValueList vl = (ValueList) inputParameters.getValue(0).values;

    	// Get the position in the list from the second argument
    	IntegerValue iv = (IntegerValue) inputParameters.getValue(1).values.getValue(0);
    	int position = iv.value;
		Debug.println("[doBody] List Get, looking for element at index: " + position);
    	
    	// The position must be 1 or greater, since index is 1-based
    	if (position < 1) {
    		Debug.println("[doBody] List Get, invalid value for index (return nothing): " + position);
    		// invalid value for 1-based index, return empty list
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    		return;
    	}
    	
    	// Find the element based on position
    	try {
    		// Find the element in the list by its position.  Offset index by 1,
    		// since the underlying list is 0-based, while this API is 1-based
    		Value result = vl.getValue(position-1);
    		
    		Debug.println("[doBody] List Get, found element at index " + position);

    		// Add output to the outputParameters list.  The output can be a
    		// reference to the original object in the input list.
    		LibraryFunctions.addValueToOutputList(result, outputParameters);
    	} catch (IndexOutOfBoundsException e) {
    		Debug.println("[doBody] List Get, index out of bounds error (return nothing)");
    		// If element does not exist, return empty list
    		LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
    	}    	
    }
    
    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new ListGetFunctionBehaviorExecution();
    }   

} // ListGetFunctionBehaviorExecution
