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

package fuml.semantics.structuredclassifiers;

import fuml.semantics.commonbehavior.CallEventBehavior;

public abstract class DispatchStrategy extends
		fuml.semantics.loci.SemanticStrategy {

	public String getName() {
		// Dispatch strategies are always named "dispatch".

		return "dispatch";
	} // getName

	public fuml.semantics.commonbehavior.Execution dispatch(
			fuml.semantics.structuredclassifiers.Object_ object,
			fuml.syntax.classification.Operation operation) {
		// Get the behavior for the given operation as determined by the type(s)
		// of the given object, compile the behavior at the locus of the object,
		// and return the resulting execution object.

		return object.locus.factory.createExecution(this.getMethod(object, operation), object);
	} // dispatch

	public fuml.syntax.commonbehavior.Behavior getMethod(
			fuml.semantics.structuredclassifiers.Object_ object,
			fuml.syntax.classification.Operation operation) {
		// Get the method that corresponds to the given operation for the given object.
		// By default, the operation is treated as being called via a call event occurrence,
		// with a call even behavior as its effective method. Concrete dispatch strategy
		// subclasses may override this default to provide other dispatching behavior.

		CallEventBehavior method = new CallEventBehavior();
		method.setOperation(operation);
		return method;
	}
	
} // DispatchStrategy
