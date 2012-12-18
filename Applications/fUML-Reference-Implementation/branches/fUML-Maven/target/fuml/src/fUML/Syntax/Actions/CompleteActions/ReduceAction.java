
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

public class ReduceAction extends fUML.Syntax.Actions.BasicActions.Action {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior reducer = null;
	public fUML.Syntax.Actions.BasicActions.OutputPin result = null;
	public fUML.Syntax.Actions.BasicActions.InputPin collection = null;
	public boolean isOrdered = false;

	public void setIsOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	} // setIsOrdered

	public void setReducer(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior reducer) {
		this.reducer = reducer;
	} // setReducer

	public void setCollection(
			fUML.Syntax.Actions.BasicActions.InputPin collection) {
		super.addInput(collection);
		this.collection = collection;
	} // setCollection

	public void setResult(fUML.Syntax.Actions.BasicActions.OutputPin result) {
		super.addOutput(result);
		this.result = result;
	} // setResult

} // ReduceAction
