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

import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import fUML.Semantics.CommonBehaviors.Communications.CallEventExecution;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public abstract class DispatchStrategy extends
		fUML.Semantics.Loci.LociL1.SemanticStrategy {

	public String getName() {
		// Dispatch strategies are always named "dispatch".

		return "dispatch";
	} // getName

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation) {
		// Determine the actual operation corresponding to the operation being
		// called, taking into account any operation overriding as determined
		// by the type(s) of the given object. Then, if that operation has a
		// method, create an execution for it at the locus of the object.
		// Otherwise, create a CallEventExecution so that the call may be
		// transmitted using a CallEventOccurrence.
		
		Operation actualOperation = this.getActualOperation(object, operation);
		Execution execution = null;
		
		if (actualOperation.method.size() > 0) {
			Behavior method = actualOperation.method.getValue(0);
			execution = object.locus.factory.createExecution(method, object);
		} else {
			execution = new CallEventExecution();
			execution.context = object;
			((CallEventExecution)execution).operation = actualOperation;
			object.locus.add(execution);
		}

		return execution;
	} // dispatch

	public abstract fUML.Syntax.Classes.Kernel.Operation getActualOperation(
			fUML.Semantics.Classes.Kernel.Object_ object,
			fUML.Syntax.Classes.Kernel.Operation operation);
	
} // DispatchStrategy
