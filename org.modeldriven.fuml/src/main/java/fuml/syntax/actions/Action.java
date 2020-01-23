
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

public abstract class Action extends
		fuml.syntax.activities.ExecutableNode {

	public fuml.syntax.actions.OutputPinList output = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.classification.Classifier context = null;
	public fuml.syntax.actions.InputPinList input = new fuml.syntax.actions.InputPinList();
	public boolean isLocallyReentrant = false;

	protected void addInput(fuml.syntax.actions.InputPin input) {
		super.addOwnedElement(input);
		this.input.addValue(input);
	} // addInput

	protected void addOutput(fuml.syntax.actions.OutputPin output) {
		super.addOwnedElement(output);
		this.output.addValue(output);
	} // addOutput

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.isLocallyReentrant = isLocallyReentrant;
	} // setIsLocallyReentrant

	public void _setContext(fuml.syntax.classification.Classifier context) {
		this.context = context;
	} // _setContext

	public void _setActivity(
			fuml.syntax.activities.Activity activity) {
		super._setActivity(activity);
		this._setContext(activity);
	} // _setActivity

} // Action
