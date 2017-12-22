
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

public class AcceptEventAction extends fuml.syntax.actions.Action {

	public boolean isUnmarshall = false;
	public fuml.syntax.actions.OutputPinList result = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.commonbehavior.TriggerList trigger = new fuml.syntax.commonbehavior.TriggerList();

	public void setIsUnmarshall(boolean isUnmarshall) {
		this.isUnmarshall = isUnmarshall;
	} // setIsUnmarshall

	public void addTrigger(
			fuml.syntax.commonbehavior.Trigger trigger) {
		this.trigger.addValue(trigger);
	} // addTrigger

	public void addResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result.addValue(result);
	} // addResult

} // AcceptEventAction
