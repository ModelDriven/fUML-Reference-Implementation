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

public class BooleanAndFunctionBehaviorExecution extends
	BooleanFunctionBehaviorExecution {

    public boolean doBooleanFunction(List<Boolean> arguments) {
    	boolean result = arguments.get(0) && arguments.get(1);
    	Debug.println("[doBody] Boolean And result = " + result);
    	return result;
    }
    
    @Override
	public fUML.Semantics.Classes.Kernel.Value new_() {
        return new BooleanAndFunctionBehaviorExecution();
    }	    

} // BooleanAndFunctionBehaviorExecution
