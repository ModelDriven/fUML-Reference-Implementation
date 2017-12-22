
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

public abstract class InvocationAction extends
		fuml.syntax.actions.Action {

	public fuml.syntax.actions.InputPinList argument = new fuml.syntax.actions.InputPinList();

	public void addArgument(fuml.syntax.actions.InputPin argument) {
		super.addInput(argument);
		this.argument.addValue(argument);
	} // addArgument

} // InvocationAction
