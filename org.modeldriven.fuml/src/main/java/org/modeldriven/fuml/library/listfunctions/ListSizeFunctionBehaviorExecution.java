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
import fUML.Semantics.Classes.Kernel.ValueList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>org::modeldriven::fuml::library::listfunctions::ListSizeFunctionBehaviorExecution</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ListSizeFunctionBehaviorExecution#doBody <em>doBody</em>}</li>
 * doIntegerFunction</em>}</li>
 * <li>{@link ListSizeFunctionBehaviorExecution#new_ <em>new_</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ListSizeFunctionBehaviorExecution extends
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
    	
    	// Get the list for which to determine the size
    	ValueList vl = (ValueList) inputParameters.getValue(0).values;
    	
    	// Determine the length of the list
    	int size = vl.size();
    	
    	// Return the size in an IntegerValue object
    	IntegerValue result = new IntegerValue();
    	result.value = size;

        Debug.println("[doBody] List Size, result = " + result.value);

		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(result, outputParameters);
    }
    
    /**
     * operation new_ <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new ListSizeFunctionBehaviorExecution();
    }   

} // ListSizeFunctionBehaviorExecution
