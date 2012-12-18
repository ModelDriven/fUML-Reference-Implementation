
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

public abstract class Action extends
		fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode {

	public fUML.Syntax.Actions.BasicActions.OutputPinList output = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.Classes.Kernel.Classifier context = null;
	public fUML.Syntax.Actions.BasicActions.InputPinList input = new fUML.Syntax.Actions.BasicActions.InputPinList();
	public boolean isLocallyReentrant = false;

	protected void addInput(fUML.Syntax.Actions.BasicActions.InputPin input) {
		super.addOwnedElement(input);
		this.input.addValue(input);
	} // addInput

	protected void addOutput(fUML.Syntax.Actions.BasicActions.OutputPin output) {
		super.addOwnedElement(output);
		this.output.addValue(output);
	} // addOutput

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.isLocallyReentrant = isLocallyReentrant;
	} // setIsLocallyReentrant

	public void _setContext(fUML.Syntax.Classes.Kernel.Classifier context) {
		this.context = context;
	} // _setContext

	public void _setActivity(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity) {
		super._setActivity(activity);
		this._setContext(activity);
	} // _setActivity

} // Action
