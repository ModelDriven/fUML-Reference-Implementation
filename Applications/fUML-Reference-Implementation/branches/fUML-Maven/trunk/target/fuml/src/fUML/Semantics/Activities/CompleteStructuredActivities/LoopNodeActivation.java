
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

public class LoopNodeActivation
		extends
		fUML.Semantics.Activities.CompleteStructuredActivities.StructuredActivityNodeActivation {

	public fUML.Semantics.Activities.CompleteStructuredActivities.ValuesList bodyOutputLists = new fUML.Semantics.Activities.CompleteStructuredActivities.ValuesList();
	public boolean isTerminateAll = false;
	
	public void doStructuredActivity() {
		// Set the initial values for the body outputs to the values of the loop
		// variable input pins.
		// If isTestedFirst is true, then repeatedly run the test part and the
		// body part of the loop, copying values from the body outputs to the
		// loop variables.
		// If isTestedFirst is false, then repeatedly run the body part and the
		// test part of the loop, copying values from the body outputs to the
		// loop variables.
		// When the test fails, copy the values of the body outputs to the loop
		// outputs.
		// [Note: The body outputs are used for the loop outputs, rather than
		// the loop variables, since values on the loop variables may be
		// consumed when running the test for the last time.]

		LoopNode loopNode = (LoopNode) (this.node);
		InputPinList loopVariableInputs = loopNode.loopVariableInput;
		// OutputPinList loopVariables = loopNode.loopVariable;
		// OutputPinList resultPins = loopNode.result;

		// ValuesList bodyOutputLists = this.bodyOutputLists;
		this.bodyOutputLists.clear();
		for (int i = 0; i < loopVariableInputs.size(); i++) {
			InputPin loopVariableInput = loopVariableInputs.getValue(i);
			Values bodyOutputList = new Values();
			bodyOutputList.values = this.takeTokens(loopVariableInput);
			this.bodyOutputLists.addValue(bodyOutputList);
		}

		this.isTerminateAll = false;
		this.doLoop(true);
	} // doStructuredActivity

	public void doLoop(boolean continuing) {
		// If isTestedFirst is true, then repeatedly run the test part and the
		// body part of the loop, copying values from the body outputs to the
		// loop variables.
		// If isTestedFirst is false, then repeatedly run the body part and the
		// test part of the loop, copying values from the body outputs to the
		// loop variables.

		LoopNode loopNode = (LoopNode) (this.node);
		OutputPinList loopVariables = loopNode.loopVariable;
		OutputPinList resultPins = loopNode.result;

		while (continuing) {

			// Set loop variable values
			this.runLoopVariables();
			for (int i = 0; i < loopVariables.size(); i++) {
				OutputPin loopVariable = loopVariables.getValue(i);
				Values bodyOutputList = bodyOutputLists.getValue(i);
				ValueList values = bodyOutputList.values;
				this.putPinValues(loopVariable, values);
				((OutputPinActivation) this.activationGroup
						.getNodeActivation(loopVariable)).sendUnofferedTokens();
			}

			// Run all the non-executable, non-pin nodes in the conditional
			// node.
			ActivityNodeActivationList nodeActivations = this.activationGroup.nodeActivations;
			ActivityNodeActivationList nonExecutableNodeActivations = new ActivityNodeActivationList();
			for (int i = 0; i < nodeActivations.size(); i++) {
				ActivityNodeActivation nodeActivation = nodeActivations
						.getValue(i);
				if (!(nodeActivation.node instanceof ExecutableNode | nodeActivation.node instanceof Pin)) {
					nonExecutableNodeActivations.addValue(nodeActivation);
				}
			}
			this.activationGroup.run(nonExecutableNodeActivations);

			// Run the loop
			if (loopNode.isTestedFirst) {
				continuing = this.runTest();
				if (continuing) {
					this.runBody();
				}
			} else {
				this.runBody();
				if (this.isRunning() & !this.isSuspended()) {
					continuing = this.runTest();
				}
			}

			if (!this.isTerminateAll & this.isRunning() & !this.isSuspended()) {
				this.activationGroup.terminateAll();
			} else {
				continuing = false;
			}

			Debug.println("[doStructuredActivity] "
					+ (continuing ? "Continuing."
							: this.isSuspended() ? "Suspended" : "Done."));

		}

		if (!this.isTerminateAll & this.isRunning() & !this.isSuspended()) {
			for (int i = 0; i < bodyOutputLists.size(); i++) {
				Values bodyOutputList = bodyOutputLists.getValue(i);
				OutputPin resultPin = resultPins.getValue(i);
				this.putTokens(resultPin, bodyOutputList.values);
			}
		}
	} // doLoop

	public boolean runTest() {
		// Run the test part of the loop node for this node activation.
		// Return the value on the decider pin.

		Debug.println("[runTest] Running test...");

		LoopNode loopNode = (LoopNode) (this.node);

		this.activationGroup.runNodes(this.makeActivityNodeList(loopNode.test));

		ValueList values = this.getPinValues(loopNode.decider);

		// If there is no decider value, treat it as false.
		boolean decision = false;
		if (values.size() > 0) {
			decision = ((BooleanValue) (values.getValue(0))).value;
		}

		Debug.println("[runTest] "
				+ (decision ? "Test succeeded." : "Test failed."));

		return decision;
	} // runTest

	public void runBody() {
		// Run the body part of the loop node for this node activation and save
		// the body outputs.

		Debug.println("[runBody] Running body...");

		LoopNode loopNode = (LoopNode) this.node;

		this.activationGroup.runNodes(this
				.makeActivityNodeList(loopNode.bodyPart));

		if (!this.isSuspended()) {
			this.saveBodyOutputs();
		}
	} // runBody

	public void saveBodyOutputs() {
		// Save the body outputs for use in the next iteration.

		LoopNode loopNode = (LoopNode) this.node;
		OutputPinList bodyOutputs = loopNode.bodyOutput;
		ValuesList bodyOutputLists = this.bodyOutputLists;
		for (int i = 0; i < bodyOutputs.size(); i++) {
			OutputPin bodyOutput = bodyOutputs.getValue(i);
			Values bodyOutputList = bodyOutputLists.getValue(i);
			bodyOutputList.values = this.getPinValues(bodyOutput);
		}
	} // saveBodyOutputs

	public void runLoopVariables() {
		// Run the loop variable pins of the loop node for this node activation.

		this.activationGroup.runNodes(this.makeLoopVariableList());
	} // runLoopVariables

	public void createNodeActivations() {
		// In addition to creating activations for contained nodes, create
		// activations for any loop variables.

		super.createNodeActivations();
		this.activationGroup.createNodeActivations(this.makeLoopVariableList());
	} // createNodeActivations

	public fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList makeLoopVariableList() {
		// Return an activity node list containing the loop variable pins for
		// the loop node of this activation.

		LoopNode loopNode = (LoopNode) (this.node);
		ActivityNodeList nodes = new ActivityNodeList();

		OutputPinList loopVariables = loopNode.loopVariable;
		for (int i = 0; i < loopVariables.size(); i++) {
			OutputPin loopVariable = loopVariables.getValue(i);
			nodes.addValue(loopVariable);
		}

		return nodes;

	} // makeLoopVariableList

	public void terminateAll() {
		// Copy the values of the body outputs to the loop outputs, and then
		// terminate all activations in the loop.

		this.isTerminateAll = true;
		
		OutputPinList resultPins = ((LoopNode) this.node).result;

		for (int i = 0; i < bodyOutputLists.size(); i++) {
			Values bodyOutputList = bodyOutputLists.getValue(i);
			OutputPin resultPin = resultPins.getValue(i);
			this.putTokens(resultPin, bodyOutputList.values);
		}

		super.terminateAll();
	} // terminateAll

	public void resume() {
		// When this loop node is resumed after being suspended, continue with
		// its next iteration (if any). Once the loop has completed execution
		// without being suspended again, complete the action.

		LoopNode loopNode = (LoopNode) (this.node);

		this.saveBodyOutputs();

		if (!this.isTerminateAll) {
			if (loopNode.mustIsolate) {
				_beginIsolation();
				this.continueLoop();
				_endIsolation();
			} else {
				this.continueLoop();
			}
		}

		if (this.isSuspended()) {
			// NOTE: If the subsequent iteration of the loop suspends it again,
			// then it is necessary to remove the previous suspension from the
			// containing activity node activation group.
			this.group.resume(this);
		} else {
			super.resume();
		}
	} // resume

	public void continueLoop() {
		// Continue the loop node when it is resumed after being suspended. If
		// isTestedFirst is true, then continue executing the loop. If
		// isTestedFirst is false, then run the test to determine whether
		// the loop should be continued or completed.
		// [Note that this presumes that an accept event action is not allowed
		// in the test part of a loop node.]

		LoopNode loopNode = (LoopNode) (this.node);

		boolean continuing = true;
		if (!loopNode.isTestedFirst) {
			continuing = this.runTest();
		}

		if (this.isRunning()) {
			this.activationGroup.terminateAll();
			this.doLoop(continuing);
		}
	} // continueLoop

} // LoopNodeActivation
