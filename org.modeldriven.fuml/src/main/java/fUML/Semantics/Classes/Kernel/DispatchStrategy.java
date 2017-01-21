/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Classes.Kernel;

import fUML.Semantics.CommonBehaviors.Communications.CallEventBehavior;

public abstract class DispatchStrategy extends
		fUML.Semantics.Loci.LociL1.SemanticStrategy {

	public String getName() {
		// Dispatch strategies are always named "dispatch".

		return "dispatch";
	} // getName

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation) {
		// Get the behavior for the given operation as determined by the type(s)
		// of the given object, compile the behavior at the locus of the object,
		// and return the resulting execution object.

		return object.locus.factory.createExecution(this.getMethod(object, operation), object);
	} // dispatch

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior getMethod(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation) {
		// Get the method that corresponds to the given operation for the given object.
		// By default, the operation is treated as being called via a call event occurrence,
		// with a call even behavior as its effective method. Concrete dispatch strategy
		// subclasses may override this default to provide other dispatching behavior.

		CallEventBehavior method = new CallEventBehavior();
		method.setOperation(operation);
		return method;
	}
	
} // DispatchStrategy
