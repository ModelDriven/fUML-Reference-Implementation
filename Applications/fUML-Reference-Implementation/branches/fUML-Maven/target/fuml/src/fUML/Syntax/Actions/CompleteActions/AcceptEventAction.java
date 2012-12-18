
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

package fUML.Syntax.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class AcceptEventAction extends fUML.Syntax.Actions.BasicActions.Action {

	public boolean isUnmarshall = false;
	public fUML.Syntax.Actions.BasicActions.OutputPinList result = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.CommonBehaviors.Communications.TriggerList trigger = new fUML.Syntax.CommonBehaviors.Communications.TriggerList();

	public void setIsUnmarshall(boolean isUnmarshall) {
		this.isUnmarshall = isUnmarshall;
	} // setIsUnmarshall

	public void addTrigger(
			fUML.Syntax.CommonBehaviors.Communications.Trigger trigger) {
		this.trigger.addValue(trigger);
	} // addTrigger

	public void addResult(fUML.Syntax.Actions.BasicActions.OutputPin result) {
		super.addOutput(result);
		this.result.addValue(result);
	} // addResult

} // AcceptEventAction
