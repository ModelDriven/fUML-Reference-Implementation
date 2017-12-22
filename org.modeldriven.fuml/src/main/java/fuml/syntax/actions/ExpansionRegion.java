
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

public class ExpansionRegion
		extends
		fuml.syntax.actions.StructuredActivityNode {

	public fuml.syntax.actions.ExpansionKind mode = fuml.syntax.actions.ExpansionKind.iterative;
	public fuml.syntax.actions.ExpansionNodeList outputElement = new fuml.syntax.actions.ExpansionNodeList();
	public fuml.syntax.actions.ExpansionNodeList inputElement = new fuml.syntax.actions.ExpansionNodeList();

	public void setMode(
			fuml.syntax.actions.ExpansionKind mode) {
		this.mode = mode;
	} // setMode

	public void addInputElement(
			fuml.syntax.actions.ExpansionNode inputElement) {
		this.inputElement.addValue(inputElement);
		inputElement._setRegionAsInput(this);
	} // addInputElement

	public void addOutputElement(
			fuml.syntax.actions.ExpansionNode outputElement) {
		this.outputElement.addValue(outputElement);
		outputElement._setRegionAsOutput(this);
	} // addOutputElement

} // ExpansionRegion
