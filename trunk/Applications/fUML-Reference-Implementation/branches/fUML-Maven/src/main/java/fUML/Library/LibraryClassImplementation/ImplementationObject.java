
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

package fUML.Library.LibraryClassImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class ImplementationObject extends
		fUML.Semantics.Classes.Kernel.Object_ {

	public abstract void execute(
			fUML.Library.LibraryClassImplementation.OperationExecution execution);

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
			fUML.Syntax.Classes.Kernel.Operation operation) {
		OperationExecution execution = new OperationExecution();
		this.locus.add(execution);
		execution.set(this, operation);
		return execution;
	} // dispatch

} // ImplementationObject
