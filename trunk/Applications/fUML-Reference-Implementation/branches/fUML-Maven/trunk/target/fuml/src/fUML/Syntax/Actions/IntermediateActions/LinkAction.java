
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

package fUML.Syntax.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class LinkAction extends
		fUML.Syntax.Actions.BasicActions.Action {

	public fUML.Syntax.Actions.IntermediateActions.LinkEndDataList endData = new fUML.Syntax.Actions.IntermediateActions.LinkEndDataList();
	public fUML.Syntax.Actions.BasicActions.InputPinList inputValue = new fUML.Syntax.Actions.BasicActions.InputPinList();

	public void addEndData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndData endData) {
		this.endData.addValue(endData);
	} // addEndData

	public void addInputValue(
			fUML.Syntax.Actions.BasicActions.InputPin inputValue) {
		super.addInput(inputValue);
		this.inputValue.addValue(inputValue);
	} // addInputValue

} // LinkAction
