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

import UMLPrimitiveTypes.intList;
import fUML.Debug;

public class UnlimitedNaturalLessThanFunctionBehaviorExecution extends
	UnlimitedNaturalRelationalFunctionBehaviorExecution {

	@Override
    public boolean doUnlimitedNaturalFunction(intList arguments) {
		
		int i1 = arguments.get(0);
		int i2 = arguments.get(1);
		
    	// This function returns true if i1 < i2, where a value of -1 means 
		// "unbounded", which is the highest possible value.
    	
		boolean result = i1 >= 0 && (i2 < 0 || i1 < i2);
    	
    	Debug.println("[doBody] Unlimited Natural Less Than result = " + result);
    	return result;
    }

	@Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new UnlimitedNaturalLessThanFunctionBehaviorExecution();
    }

} // UnlimitedNaturalLessThanFunctionBehaviorExecution
