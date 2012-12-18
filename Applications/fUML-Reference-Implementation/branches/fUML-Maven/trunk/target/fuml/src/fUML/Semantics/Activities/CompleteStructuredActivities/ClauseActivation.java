
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

package fUML.Semantics.Activities.CompleteStructuredActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import java.util.Iterator;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ClauseActivation extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Activities.CompleteStructuredActivities.ConditionalNodeActivation conditionalNodeActivation = null;
	public fUML.Syntax.Activities.CompleteStructuredActivities.Clause clause = null;

	public void receiveControl() {
		// If all predecessors to the clause for this activation have run their
		// tests and failed, then run the test for this clause.
		// If the test succeeds, then terminate any other clauses that may be
		// running and run the body of this clause.
		// If the test fails, then pass control to successor clauses.

		Debug.println("[receiveControl] clauseActivation = " + this);

		if (this.isReady()) {
			Debug.println("[receiveControl] Running test...");
			this.runTest();

			BooleanValue decision = this.getDecision();

			// Note that the decision may be null if the test was terminated
			// before completion.
			if (decision != null) {
				if (decision.value == true) {
					Debug.println("[receiveControl] Test succeeded.");
					this.selectBody();
				} else {
					Debug.println("[receiveControl] Test failed.");

					ClauseActivationList successors = this.getSuccessors();

					// *** Give control to all successors concurrently. ***
					for (Iterator i = successors.iterator(); i.hasNext();) {
						ClauseActivation successor = (ClauseActivation) i
								.next();
						successor.receiveControl();
					}
				}
			}
		}
	} // receiveControl

	public boolean isReady() {
		// Test if all predecessors to this clause activation have failed.

		ClauseActivationList predecessors = this.getPredecessors();

		boolean ready = true;
		int i = 1;
		while (ready & i <= predecessors.size()) {
			ClauseActivation predecessor = predecessors.getValue(i - 1);
			BooleanValue decisionValue = predecessor.getDecision();

			// Note that the decision will be null if the predecessor clause has
			// not run yet.
			if (decisionValue == null) {
				ready = false;
			} else {
				ready = !decisionValue.value;
			}

			i = i + 1;
		}

		return ready;

	} // isReady

	public void runTest() {
		// Run the test of the clause for this clause activation.

		this.conditionalNodeActivation.runTest(this.clause);
	} // runTest

	public void selectBody() {
		// Select the body of the clause for this clause activation.

		this.conditionalNodeActivation.selectBody(this.clause);
	} // selectBody

	public fUML.Semantics.Classes.Kernel.BooleanValue getDecision() {
		// Get the value (if any) on the decider pin of the clause for this
		// clause activation.

		ValueList deciderValues = this.conditionalNodeActivation
				.getPinValues(this.clause.decider);

		BooleanValue deciderValue = null;
		if (deciderValues.size() > 0) {
			deciderValue = (BooleanValue) (deciderValues.getValue(0));
		}

		return deciderValue;
	} // getDecision

	public fUML.Semantics.Activities.CompleteStructuredActivities.ClauseActivationList getPredecessors() {
		// Return the clause activations for the predecessors of the clause for
		// this clause activation.

		ClauseActivationList predecessors = new ClauseActivationList();

		ClauseList predecessorClauses = this.clause.predecessorClause;
		for (int i = 0; i < predecessorClauses.size(); i++) {
			Clause predecessorClause = predecessorClauses.getValue(i);
			predecessors.addValue(this.conditionalNodeActivation
					.getClauseActivation(predecessorClause));
		}

		return predecessors;
	} // getPredecessors

	public fUML.Semantics.Activities.CompleteStructuredActivities.ClauseActivationList getSuccessors() {
		// Return the clause activations for the successors of the clause for
		// this clause activation.

		ClauseActivationList successors = new ClauseActivationList();

		ClauseList successorClauses = this.clause.successorClause;
		for (int i = 0; i < successorClauses.size(); i++) {
			Clause successorClause = successorClauses.getValue(i);
			successors.addValue(this.conditionalNodeActivation
					.getClauseActivation(successorClause));
		}

		return successors;

	} // getSuccessors

} // ClauseActivation
