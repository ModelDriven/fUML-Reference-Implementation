
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

public class LoopNode
		extends
		fuml.syntax.actions.StructuredActivityNode {

	public boolean isTestedFirst = false;
	public fuml.syntax.actions.OutputPin decider = null;
	public fuml.syntax.actions.ExecutableNodeList test = new fuml.syntax.actions.ExecutableNodeList();
	public fuml.syntax.actions.OutputPinList bodyOutput = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.actions.InputPinList loopVariableInput = new fuml.syntax.actions.InputPinList();
	public fuml.syntax.actions.ExecutableNodeList bodyPart = new fuml.syntax.actions.ExecutableNodeList();
	public fuml.syntax.actions.OutputPinList result = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.actions.OutputPinList loopVariable = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.actions.ExecutableNodeList setupPart = new fuml.syntax.actions.ExecutableNodeList();

	public void setIsTestedFirst(boolean isTestedFirst) {
		this.isTestedFirst = isTestedFirst;
	} // setIsTestedFirst

	public void addTest(
			fuml.syntax.actions.ExecutableNode test) {
		this.test.addValue(test);
	} // addTest

	public void addBodyPart(
			fuml.syntax.actions.ExecutableNode bodyPart) {
		this.bodyPart.addValue(bodyPart);
	} // addBodyPart

	public void addLoopVariableInput(
			fuml.syntax.actions.InputPin loopVariableInput) {
		super.addStructuredNodeInput(loopVariableInput);
		this.loopVariableInput.addValue(loopVariableInput);
	} // addLoopVariableInput

	public void addLoopVariable(
			fuml.syntax.actions.OutputPin loopVariable) {
		this.loopVariable.addValue(loopVariable);
	} // addLoopVariable

	public void setDecider(fuml.syntax.actions.OutputPin decider) {
		this.decider = decider;
	} // setDecider

	public void addBodyOutput(
			fuml.syntax.actions.OutputPin bodyOutput) {
		this.bodyOutput.addValue(bodyOutput);
	} // addBodyOutput

	public void addResult(fuml.syntax.actions.OutputPin result) {
		super.addOutput(result);
		this.result.addValue(result);
	} // addResult

} // LoopNode
