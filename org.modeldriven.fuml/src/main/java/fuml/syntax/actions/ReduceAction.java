
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

public class ReduceAction extends fuml.syntax.actions.Action {

	public fuml.syntax.commonbehavior.Behavior reducer = null;
	public fuml.syntax.actions.OutputPin result = null;
	public fuml.syntax.actions.InputPin collection = null;
	public boolean isOrdered = false;

	public void setIsOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	} // setIsOrdered

	public void setReducer(
			fuml.syntax.commonbehavior.Behavior reducer) {
		this.reducer = reducer;
	} // setReducer

	public void setCollection(
			fuml.syntax.actions.InputPin collection) {
		super.addInput(collection);
		this.collection = collection;
	} // setCollection

	public void setResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result = result;
	} // setResult

} // ReduceAction
