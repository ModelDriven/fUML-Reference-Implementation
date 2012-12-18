
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

public class SendSignalAction extends
		fUML.Syntax.Actions.BasicActions.InvocationAction {

	public fUML.Syntax.Actions.BasicActions.InputPin target = null;
	public fUML.Syntax.CommonBehaviors.Communications.Signal signal = null;

	public void setTarget(fUML.Syntax.Actions.BasicActions.InputPin target) {
		super.addInput(target);
		this.target = target;
	} // setTarget

	public void setSignal(
			fUML.Syntax.CommonBehaviors.Communications.Signal signal) {
		this.signal = signal;
	} // setSignal

} // SendSignalAction
