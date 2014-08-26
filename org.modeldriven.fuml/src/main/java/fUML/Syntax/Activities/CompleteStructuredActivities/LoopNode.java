
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

package fUML.Syntax.Activities.CompleteStructuredActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class LoopNode
		extends
		fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode {

	public boolean isTestedFirst = false;
	public fUML.Syntax.Actions.BasicActions.OutputPin decider = null;
	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList test = new fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList();
	public fUML.Syntax.Actions.BasicActions.OutputPinList bodyOutput = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.Actions.BasicActions.InputPinList loopVariableInput = new fUML.Syntax.Actions.BasicActions.InputPinList();
	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList bodyPart = new fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList();
	public fUML.Syntax.Actions.BasicActions.OutputPinList result = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.Actions.BasicActions.OutputPinList loopVariable = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList setupPart = new fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList();

	public void setIsTestedFirst(boolean isTestedFirst) {
		this.isTestedFirst = isTestedFirst;
	} // setIsTestedFirst

	public void addTest(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode test) {
		this.test.addValue(test);
	} // addTest

	public void addBodyPart(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode bodyPart) {
		this.bodyPart.addValue(bodyPart);
	} // addBodyPart

	public void addLoopVariableInput(
			fUML.Syntax.Actions.BasicActions.InputPin loopVariableInput) {
		super.addStructuredNodeInput(loopVariableInput);
		this.loopVariableInput.addValue(loopVariableInput);
	} // addLoopVariableInput

	public void addLoopVariable(
			fUML.Syntax.Actions.BasicActions.OutputPin loopVariable) {
		this.loopVariable.addValue(loopVariable);
	} // addLoopVariable

	public void setDecider(fUML.Syntax.Actions.BasicActions.OutputPin decider) {
		this.decider = decider;
	} // setDecider

	public void addBodyOutput(
			fUML.Syntax.Actions.BasicActions.OutputPin bodyOutput) {
		this.bodyOutput.addValue(bodyOutput);
	} // addBodyOutput

	public void addResult(fUML.Syntax.Actions.BasicActions.OutputPin result) {
		super.addOutput(result);
		this.result.addValue(result);
	} // addResult

} // LoopNode
