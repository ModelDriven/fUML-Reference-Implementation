
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

public abstract class WriteStructuralFeatureAction extends
		fuml.syntax.actions.StructuralFeatureAction {

	public fuml.syntax.actions.InputPin value = null;
	public fuml.syntax.actions.OutputPin result = null;

	public void setResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result = result;
	} // setResult

	public void setValue(fuml.syntax.actions.InputPin value) {
		super.addInput(value);
		this.value = value;
	} // setValue

} // WriteStructuralFeatureAction
