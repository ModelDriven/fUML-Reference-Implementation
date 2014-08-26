
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Library.IntegerFunctionImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class IntegerPlusFunctionBehaviorExecution
		extends
		fUML.Library.IntegerFunctionImplementation.IntegerFunctionBehaviorExecution {

	public int doIntegerFunction(UMLPrimitiveTypes.intList arguments) {
		// Compute the integer plus function.

		// Debug.println("[doIntegerFunction] arguments[0] = " +
		// arguments.getValue(0) + ", arguments[1] = " + arguments.getValue(1));
		// Debug.println("[doIntegerFunction] sum = " + (arguments.getValue(0) +
		// arguments.getValue(1)));

		return arguments.getValue(0) + arguments.getValue(1);
	} // doIntegerFunction

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new instance of this kind of function behavior execution.

		return new IntegerPlusFunctionBehaviorExecution();
	} // new_

} // IntegerPlusFunctionBehaviorExecution
