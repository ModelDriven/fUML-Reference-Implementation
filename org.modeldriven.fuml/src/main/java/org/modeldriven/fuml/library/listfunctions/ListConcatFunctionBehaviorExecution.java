/*
 * Copyright 2011 Data Access Technologies, Inc. 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.listfunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class ListConcatFunctionBehaviorExecution extends OpaqueBehaviorExecution {

	@Override
    public void doBody(
            ParameterValueList inputParameters,
            ParameterValueList outputParameters) {
    	
    	ValueList list1 = (ValueList) inputParameters.get(0).values;
    	ValueList list2 = (ValueList) inputParameters.get(1).values;
    	
    	ValueList result = new ValueList();
    	result.addAll(list1);
    	result.addAll(list2);

        Debug.println("[doBody] List Concat, result=" + result);

		outputParameters.get(0).values = result;
    }
    
    public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new ListConcatFunctionBehaviorExecution();
    }   

} // ListSizeFunctionBehaviorExecution
