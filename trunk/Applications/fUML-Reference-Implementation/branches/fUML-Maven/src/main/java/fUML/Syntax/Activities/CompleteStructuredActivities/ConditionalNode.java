
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

public class ConditionalNode
		extends
		fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode {

	public boolean isDeterminate = false;
	public boolean isAssured = false;
	public fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList clause = new fUML.Syntax.Activities.CompleteStructuredActivities.ClauseList();
	public fUML.Syntax.Actions.BasicActions.OutputPinList result = new fUML.Syntax.Actions.BasicActions.OutputPinList();

	public void setIsDeterminate(boolean isDeterminate) {
		this.isDeterminate = isDeterminate;
	} // setIsDeterminate

	public void setIsAssured(boolean isAssured) {
		this.isAssured = isAssured;
	} // setIsAssured

	public void addClause(
			fUML.Syntax.Activities.CompleteStructuredActivities.Clause clause) {
		super.addOwnedElement(clause);
		this.clause.addValue(clause);
	} // addClause

	public void addResult(fUML.Syntax.Actions.BasicActions.OutputPin result) {
		super.addStructuredNodeOutput(result);
		this.result.addValue(result);
	} // addResult

} // ConditionalNode
