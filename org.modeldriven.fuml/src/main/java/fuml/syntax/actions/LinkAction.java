
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

public abstract class LinkAction extends
		fuml.syntax.actions.Action {

	public fuml.syntax.actions.LinkEndDataList endData = new fuml.syntax.actions.LinkEndDataList();
	public fuml.syntax.actions.InputPinList inputValue = new fuml.syntax.actions.InputPinList();

	public void addEndData(
			fuml.syntax.actions.LinkEndData endData) {
		this.endData.addValue(endData);
	} // addEndData

	public void addInputValue(
			fuml.syntax.actions.InputPin inputValue) {
		super.addInput(inputValue);
		this.inputValue.addValue(inputValue);
	} // addInputValue

} // LinkAction
