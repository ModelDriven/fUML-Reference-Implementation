
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

public class Clause extends fUML.Syntax.Classes.Kernel.Element {

	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList test = new fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList();
	public fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList body = new fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNodeList();
	public fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList predecessorClause = new fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList();
	public fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList successorClause = new fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList();
	public fUML.Syntax.Actions.BasicActions.OutputPin decider = null;
	public fUML.Syntax.Actions.BasicActions.OutputPinList bodyOutput = new fUML.Syntax.Actions.BasicActions.OutputPinList();

	public void addPredecessorClause(
			fUML.Syntax.Activities.CompleteStructuredActivities.Clause predecessorClause) {
		this.predecessorClause.addValue(predecessorClause);
		predecessorClause.successorClause.addValue(this);
	} // addPredecessorClause

	public void addTest(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode test) {
		this.test.addValue(test);
	} // addTest

	public void addBody(
			fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode body) {
		this.body.addValue(body);
	} // addBody

	public void setDecider(fUML.Syntax.Actions.BasicActions.OutputPin decider) {
		this.decider = decider;
	} // setDecider

	public void addBodyOutput(
			fUML.Syntax.Actions.BasicActions.OutputPin bodyOutput) {
		this.bodyOutput.addValue(bodyOutput);
	} // addBodyOutput

} // Clause
