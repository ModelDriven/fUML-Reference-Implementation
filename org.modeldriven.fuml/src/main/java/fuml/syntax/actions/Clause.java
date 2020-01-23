
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

public class Clause extends fuml.syntax.commonstructure.Element {

	public fuml.syntax.activities.ExecutableNodeList test = new fuml.syntax.activities.ExecutableNodeList();
	public fuml.syntax.activities.ExecutableNodeList body = new fuml.syntax.activities.ExecutableNodeList();
	public fuml.syntax.actions.ClauseList predecessorClause = new fuml.syntax.actions.ClauseList();
	public fuml.syntax.actions.ClauseList successorClause = new fuml.syntax.actions.ClauseList();
	public fuml.syntax.actions.OutputPin decider = null;
	public fuml.syntax.actions.OutputPinList bodyOutput = new fuml.syntax.actions.OutputPinList();

	public void addPredecessorClause(
			fuml.syntax.actions.Clause predecessorClause) {
		this.predecessorClause.addValue(predecessorClause);
		predecessorClause.successorClause.addValue(this);
	} // addPredecessorClause

	public void addTest(
			fuml.syntax.activities.ExecutableNode test) {
		this.test.addValue(test);
	} // addTest

	public void addBody(
			fuml.syntax.activities.ExecutableNode body) {
		this.body.addValue(body);
	} // addBody

	public void setDecider(fuml.syntax.actions.OutputPin decider) {
		this.decider = decider;
	} // setDecider

	public void addBodyOutput(
			fuml.syntax.actions.OutputPin bodyOutput) {
		this.bodyOutput.addValue(bodyOutput);
	} // addBodyOutput

} // Clause
