
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

package fuml.syntax.actions;

public class CallOperationAction extends
		fuml.syntax.actions.CallAction {

	public fuml.syntax.classification.Operation operation = null;
	public fuml.syntax.actions.InputPin target = null;

	public void setTarget(fuml.syntax.actions.InputPin target) {
		super.addInput(target);
		this.target = target;
	} // setTarget

	public void setOperation(fuml.syntax.classification.Operation operation) {
		this.operation = operation;
	} // setOperation

} // CallOperationAction
