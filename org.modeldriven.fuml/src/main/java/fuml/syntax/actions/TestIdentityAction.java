
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

public class TestIdentityAction extends fuml.syntax.actions.Action {

	public fuml.syntax.actions.InputPin second = null;
	public fuml.syntax.actions.OutputPin result = null;
	public fuml.syntax.actions.InputPin first = null;

	public void setFirst(fuml.syntax.actions.InputPin first) {
		super.addInput(first);
		this.first = first;
	} // setFirst

	public void setSecond(fuml.syntax.actions.InputPin second) {
		super.addInput(second);
		this.second = second;
	} // setSecond

	public void setResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result = result;
	} // setResult

} // TestIdentityAction
