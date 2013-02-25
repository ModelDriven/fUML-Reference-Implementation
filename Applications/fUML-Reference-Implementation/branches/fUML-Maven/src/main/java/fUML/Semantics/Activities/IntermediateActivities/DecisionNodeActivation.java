
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

package fUML.Semantics.Activities.IntermediateActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class DecisionNodeActivation extends
		fUML.Semantics.Activities.IntermediateActivities.ControlNodeActivation {

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution decisionInputExecution = null;

	public void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// Get the decision values and test them on each guard.
		// Forward the offer over the edges for which the test succeeds.

		Debug.println("[fire] Decision node " + this.node.name + "...");

		// TokenList incomingTokens = this.takeOfferedTokens();
		TokenList removedControlTokens = this
				.removeJoinedControlTokens(incomingTokens);
		ValueList decisionValues = this.getDecisionValues(incomingTokens);

		ActivityEdgeInstanceList outgoingEdges = this.outgoingEdges;
		for (int i = 0; i < outgoingEdges.size(); i++) {
			ActivityEdgeInstance edgeInstance = outgoingEdges.getValue(i);
			ValueSpecification guard = edgeInstance.edge.guard;

			TokenList offeredTokens = new TokenList();
			for (int j = 0; j < incomingTokens.size(); j++) {
				Token incomingToken = incomingTokens.getValue(j);
				Value decisionValue = decisionValues.getValue(j);
				if (this.test(guard, decisionValue)) {
					offeredTokens.addValue(incomingToken);
				}
			}

			if (offeredTokens.size() > 0) {
				for (int j = 0; j < removedControlTokens.size(); j++) {
					Token removedControlToken = removedControlTokens
							.getValue(j);
					offeredTokens.addValue(removedControlToken);
				}
				edgeInstance.sendOffer(offeredTokens);
			}
		}
	} // fire

	public fUML.Semantics.Classes.Kernel.ValueList getDecisionValues(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// If there is neither a decision input flow nor a decision input
		// behavior, then return the set of values from the incoming tokens.
		// [In this case, the single incoming edge must be an object flow.]
		// If there is a decision input flow, but no decision input behavior,
		// then return a list of the decision input values equal in size to the
		// number of incoming tokens.
		// If there is both a decision input flow and a decision input behavior,
		// then execute the decision input behavior once for each incoming token
		// and return the set of resulting values.
		// If the primary incoming edge is an object flow, then the value on
		// each object token is passed to the decision input behavior, along
		// with the decision input flow value, if any.
		// If the primary incoming edge is a control flow, then the decision
		// input behavior only receives the decision input flow, if any.

		Value decisionInputValue = this.getDecisionInputFlowValue();

		ValueList decisionValues = new ValueList();
		for (int i = 0; i < incomingTokens.size(); i++) {
			Token incomingToken = incomingTokens.getValue(i);
			Value value = this.executeDecisionInputBehavior(incomingToken
					.getValue(), decisionInputValue);
			decisionValues.addValue(value);
		}

		// Debug.println("[getDecisionValues] " + decisionValues.size() +
		// " decision value(s):");
		for (int i = 0; i < decisionValues.size(); i++) {
			Value decisionValue = decisionValues.getValue(i);
			Debug.println("[getDecisionValues] decisionValues[" + i + "] = "
					+ decisionValue);
		}

		return decisionValues;
	} // getDecisionValues

	public fUML.Semantics.Classes.Kernel.Value executeDecisionInputBehavior(
			fUML.Semantics.Classes.Kernel.Value inputValue,
			fUML.Semantics.Classes.Kernel.Value decisionInputValue) {
		// Create the decision input execution from the decision input behavior.
		// If the behavior has input parameter(s), set the input parameter(s) of
		// the execution to the given value(s).
		// Execute the decision input execution and then remove it.
		// Return the value of the output parameter of the execution.
		// If there is no decision input behavior, the decision input value is
		// returned, if one is given, otherwise the input value is used as the
		// decision value.

		Debug.println("[executeDecisionBehavior] inputValue = " + inputValue);

		Behavior decisionInputBehavior = ((DecisionNode) (this.node)).decisionInput;

		Value decisionInputResult = null;

		if (decisionInputBehavior == null) {

			if (decisionInputValue != null) {
				decisionInputResult = decisionInputValue;
			} else {
				decisionInputResult = inputValue;
			}

		} else {

			this.decisionInputExecution = this.getExecutionLocus().factory
					.createExecution(decisionInputBehavior, this
							.getExecutionContext());

			int i = 1;
			int j = 0;
			while ((j == 0 | (j == 1 & decisionInputValue != null))
					& i <= decisionInputBehavior.ownedParameter.size()) {
				Parameter parameter = decisionInputBehavior.ownedParameter
						.getValue(i - 1);
				if (parameter.direction.equals(ParameterDirectionKind.in)
						| parameter.direction
								.equals(ParameterDirectionKind.inout)) {
					ParameterValue inputParameterValue = new ParameterValue();
					inputParameterValue.parameter = parameter;

					j = j + 1;
					if (j == 1 && inputValue != null) {
						inputParameterValue.values.addValue(inputValue);
					} else {
						inputParameterValue.values.addValue(decisionInputValue);
					}

					this.decisionInputExecution
							.setParameterValue(inputParameterValue);

				}
				i = i + 1;
			}

			this.decisionInputExecution.execute();

			ParameterValueList outputParameterValues = this.decisionInputExecution
					.getOutputParameterValues();
			decisionInputExecution.destroy();

			decisionInputResult = outputParameterValues.getValue(0).values
					.getValue(0);

		}

		return decisionInputResult;
	} // executeDecisionInputBehavior

	public void terminate() {
		// Terminate the decision input execution, if any, and then terminate
		// this activation.

		if (this.decisionInputExecution != null) {
			this.decisionInputExecution.terminate();
		}

		super.terminate();
	} // terminate

	public boolean isReady() {
		// Check that all incoming edges have sources that are offering tokens.
		// [This should be at most two incoming edges, if there is a decision
		// input flow.]

		int i = 1;
		boolean ready = true;
		while (ready & i <= this.incomingEdges.size()) {
			ready = this.incomingEdges.getValue(i - 1).hasOffer();
			i = i + 1;
		}

		return ready;
	} // isReady

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeOfferedTokens() {
		// Get tokens from the incoming edge that is not the decision input
		// flow.

		ObjectFlow decisionInputFlow = ((DecisionNode) (this.node)).decisionInputFlow;

		TokenList allTokens = new TokenList();
		ActivityEdgeInstanceList incomingEdges = this.incomingEdges;
		for (int i = 0; i < incomingEdges.size(); i++) {
			ActivityEdgeInstance edgeInstance = incomingEdges.getValue(i);
			if (edgeInstance.edge != decisionInputFlow) {
				TokenList tokens = edgeInstance.takeOfferedTokens();
				for (int j = 0; j < tokens.size(); j++) {
					allTokens.addValue(tokens.getValue(j));
				}
			}
		}

		return allTokens;
	} // takeOfferedTokens

	public fUML.Semantics.Classes.Kernel.Value getDecisionInputFlowValue() {
		// Take the next token available on the decision input flow, if any, and
		// return its value.

		ActivityEdgeInstance decisionInputFlowInstance = this
				.getDecisionInputFlowInstance();

		Value value = null;
		if (decisionInputFlowInstance != null) {
			TokenList tokens = decisionInputFlowInstance.takeOfferedTokens();
			if (tokens.size() > 0) {
				value = tokens.getValue(0).getValue();
			}
		}

		return value;
	} // getDecisionInputFlowValue

	public fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance getDecisionInputFlowInstance() {
		// Get the activity edge instance for the decision input flow, if any.

		ActivityEdge decisionInputFlow = ((DecisionNode) (this.node)).decisionInputFlow;

		ActivityEdgeInstance edgeInstance = null;
		if (decisionInputFlow != null) {
			int i = 1;
			while (edgeInstance == null & i <= this.incomingEdges.size()) {
				ActivityEdgeInstance incomingEdge = this.incomingEdges
						.getValue(i - 1);
				if (incomingEdge.edge == decisionInputFlow) {
					edgeInstance = incomingEdge;
				}
				i = i + 1;
			}
		}

		return edgeInstance;
	} // getDecisionInputFlowInstance

	public boolean test(fUML.Syntax.Classes.Kernel.ValueSpecification guard,
			fUML.Semantics.Classes.Kernel.Value value) {
		// Test if the given value matches the guard. If there is no guard,
		// return true.

		boolean guardResult = true;
		if (guard != null) {
			Value guardValue = this.getExecutionLocus().executor
					.evaluate(guard);
			guardResult = guardValue.equals(value);
		}

		return guardResult;
	} // test

	public fUML.Semantics.Activities.IntermediateActivities.TokenList removeJoinedControlTokens(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// If the primary incoming edge is an object flow, then remove any
		// control tokens from the incoming tokens and return them.
		// [Control tokens may effectively be offered on an object flow outgoing
		// from a join node that has both control and object flows incoming.]

		TokenList removedControlTokens = new TokenList();

		if (this.hasObjectFlowInput()) {
			int i = 1;
			while (i <= incomingTokens.size()) {
				Token token = incomingTokens.getValue(i - 1);
				if (token.isControl()) {
					removedControlTokens.addValue(token);
					incomingTokens.removeValue(i - 1);
					i = i - 1;
				}
				i = i + 1;
			}
		}

		return removedControlTokens;
	} // removeJoinedControlTokens

	public boolean hasObjectFlowInput() {
		// Check that the primary incoming edge is an object flow.

		ActivityEdge decisionInputFlow = ((DecisionNode) (this.node)).decisionInputFlow;

		boolean isObjectFlow = false;
		int i = 1;
		while (!isObjectFlow & i <= this.incomingEdges.size()) {
			ActivityEdge edge = this.incomingEdges.getValue(i - 1).edge;
			isObjectFlow = edge != decisionInputFlow
					& edge instanceof ObjectFlow;
			i = i + 1;
		}

		return isObjectFlow;
	} // hasObjectFlowInput

} // DecisionNodeActivation
