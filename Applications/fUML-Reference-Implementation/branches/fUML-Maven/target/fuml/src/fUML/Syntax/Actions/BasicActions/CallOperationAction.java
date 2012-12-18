
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

package fUML.Syntax.Actions.BasicActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class CallOperationAction extends
		fUML.Syntax.Actions.BasicActions.CallAction {

	public fUML.Syntax.Classes.Kernel.Operation operation = null;
	public fUML.Syntax.Actions.BasicActions.InputPin target = null;

	public void setTarget(fUML.Syntax.Actions.BasicActions.InputPin target) {
		super.addInput(target);
		this.target = target;
	} // setTarget

	public void setOperation(fUML.Syntax.Classes.Kernel.Operation operation) {
		this.operation = operation;
	} // setOperation

} // CallOperationAction
