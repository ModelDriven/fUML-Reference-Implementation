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

package org.modeldriven.fuml.library.booleanfunctions;

import java.util.List;

import fUML.Debug;

public class BooleanImpliesFunctionBehaviorExecution extends
	BooleanFunctionBehaviorExecution {

    public boolean doBooleanFunction(List<Boolean> arguments) {  
    	boolean b1 = arguments.get(0);
    	boolean b2 = arguments.get(1);
    	boolean result = (!b1) || (b1 && b2);
    	Debug.println("[doBody] Boolean Implies result = " + result);
    	return result;    	
    }
    
    @Override
	public fUML.Semantics.Classes.Kernel.Value new_() {
        // Create a new instance of this kind of function behavior execution.
        return new BooleanImpliesFunctionBehaviorExecution();
    }	    

} // BooleanImpliesFunctionBehaviorExecution
