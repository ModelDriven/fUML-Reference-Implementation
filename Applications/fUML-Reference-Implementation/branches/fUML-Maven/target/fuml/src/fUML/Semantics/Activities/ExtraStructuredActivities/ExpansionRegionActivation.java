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

package fUML.Semantics.Activities.ExtraStructuredActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import java.util.Iterator;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.CommonBehaviors.Communications.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Activities.CompleteStructuredActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ExpansionRegionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroupList activationGroups = new fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroupList();
	public fUML.Semantics.Activities.ExtraStructuredActivities.TokenSetList inputTokens = new fUML.Semantics.Activities.ExtraStructuredActivities.TokenSetList();
	public fUML.Semantics.Activities.ExtraStructuredActivities.TokenSetList inputExpansionTokens = new fUML.Semantics.Activities.ExtraStructuredActivities.TokenSetList();
	public int next = 0;

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeOfferedTokens() {
		// Take the tokens from the input pin and input expansion node
		// activations and save them.

		super.takeOfferedTokens();

		ExpansionRegion region = (ExpansionRegion) (this.node);
		InputPinList inputPins = region.input;
		ExpansionNodeList inputElements = region.inputElement;

		this.inputTokens.clear();
		this.inputExpansionTokens.clear();

		for (int i = 0; i < inputPins.size(); i++) {
			InputPin inputPin = inputPins.getValue(i);
			TokenSet tokenSet = new TokenSet();
			tokenSet.tokens = this.getPinActivation(inputPin).takeTokens();
			this.inputTokens.addValue(tokenSet);
		}

		int n = this.numberOfValues();

		for (int i = 0; i < inputElements.size(); i++) {
			ExpansionNode inputElement = inputElements.getValue(i);
			ExpansionNodeActivation expansionNodeActivation = this
					.getExpansionNodeActivation(inputElement);
			expansionNodeActivation.fire(expansionNodeActivation
					.takeOfferedTokens());
			TokenList tokens = expansionNodeActivation.takeTokens();
			TokenSet tokenSet = new TokenSet();
			int j = 1;
			while (j <= n) {
				tokenSet.tokens.add(tokens.getValue(j - 1));
				j = j + 1;
			}
			this.inputExpansionTokens.addValue(tokenSet);
		}

		return new TokenList();
	} // takeOfferedTokens

	public void doAction() {
		// If the expansion region has mustIsolate=true, then carry out its
		// behavior with isolation.
		// Otherwise just activate it normally.

		if (((StructuredActivityNode) (this.node)).mustIsolate) {
			_beginIsolation();
			this.doStructuredActivity();
			_endIsolation();
		} else {
			this.doStructuredActivity();
		}

	} // doAction

	public void doStructuredActivity() {
		// Create a number of expansion region activation groups equal to the
		// number of values expanded in the region,
		// setting the region inputs and group inputs for each group.
		// Run the body of the region in each group, either iteratively or in
		// parallel.
		// Add the outputs of each activation group to the corresonding output
		// expansion node activations.

		ExpansionRegion region = (ExpansionRegion) this.node;
		InputPinList inputPins = region.input;
		ExpansionNodeList inputElements = region.inputElement;
		ExpansionNodeList outputElements = region.outputElement;

		this.activationGroups.clear();
		int n = this.inputExpansionTokens.getValue(0).tokens.size();
		int k = 1;
		while (k <= n) {
			ExpansionActivationGroup activationGroup = new ExpansionActivationGroup();
			activationGroup.regionActivation = this;
			activationGroup.index = k;

			int j = 1;
			while (j <= inputPins.size()) {
				OutputPinActivation regionInput = new OutputPinActivation();
				regionInput.run();
				activationGroup.regionInputs.addValue(regionInput);
				j = j + 1;
			}

			j = 1;
			while (j <= inputElements.size()) {
				OutputPinActivation groupInput = new OutputPinActivation();
				groupInput.run();
				activationGroup.groupInputs.addValue(groupInput);
				j = j + 1;
			}

			j = 1;
			while (j <= outputElements.size()) {
				OutputPinActivation groupOutput = new OutputPinActivation();
				groupOutput.run();
				activationGroup.groupOutputs.addValue(groupOutput);
				j = j + 1;
			}

			activationGroup.createNodeActivations(region.node);
			activationGroup.createEdgeInstances(region.edge);
			this.activationGroups.addValue(activationGroup);

			k = k + 1;
		}

		// ExpansionActivationGroupList activationGroups = this.activationGroups;

		if (region.mode == ExpansionKind.iterative) {
			Debug.println("[doStructuredActivity] Expansion mode = iterative");
			this.next = 1;
			this.runIterative();
		} else if (region.mode == ExpansionKind.parallel) {
			Debug.println("[doStructuredActivity] Expansion mode = parallel");
			this.runParallel();
		}

		this.doOutput();

	} // doStructuredActivity

	public void runIterative() {
		// Run the body of the region iteratively, either until all activation
		// groups have run or until the region is suspended.

		ExpansionActivationGroupList activationGroups = this.activationGroups;

		while (this.next <= activationGroups.size() & !this.isSuspended()) {
			ExpansionActivationGroup activationGroup = activationGroups
					.getValue(this.next - 1);
			this.runGroup(activationGroup);
			this.next = this.next + 1;
		}
	} // runIterative

	public void runParallel() {
		// Run the body of the region concurrently.

		ExpansionActivationGroupList activationGroups = this.activationGroups;

		// *** Activate all groups concurrently. ***
		for (Iterator i = activationGroups.iterator(); i.hasNext();) {
			ExpansionActivationGroup activationGroup = (ExpansionActivationGroup) i
					.next();
			this.runGroup(activationGroup);
		}
	} // runParallel

	public void doOutput() {
		// Place tokens on the output expansion nodes.

		ExpansionRegion region = (ExpansionRegion) this.node;
		ExpansionNodeList outputElements = region.outputElement;

		Debug.println("[doOutput] Expansion region " + region.name + " is "
				+ (this.isSuspended() ? "suspended." : "completed."));

		if (!this.isSuspended()) {
			for (int i = 0; i < activationGroups.size(); i++) {
				ExpansionActivationGroup activationGroup = activationGroups
						.getValue(i);
				OutputPinActivationList groupOutputs = activationGroup.groupOutputs;
				for (int j = 0; j < groupOutputs.size(); j++) {
					OutputPinActivation groupOutput = groupOutputs.getValue(j);
					ExpansionNode outputElement = outputElements.getValue(j);
					this.getExpansionNodeActivation(outputElement).addTokens(
							groupOutput.takeTokens());
				}
			}
		}
	} // doOutput

	public void terminate() {
		// Terminate the execution of all contained node activations (which
		// completes the performance of the expansion region activation).

		ExpansionActivationGroupList activationGroups = this.activationGroups;
		for (int i = 0; i < activationGroups.size(); i++) {
			ExpansionActivationGroup activationGroup = this.activationGroups
					.getValue(i);

			OutputPinActivationList groupOutputs = activationGroup.groupOutputs;

			_beginIsolation();
			for (int j = 0; j < groupOutputs.size(); j++) {
				OutputPinActivation groupOutput = groupOutputs.getValue(j);
				groupOutput.fire(groupOutput.takeOfferedTokens());
			}
			activationGroup.terminateAll();
			_endIsolation();
		}

		super.terminate();
	} // terminate

	public void sendOffers() {
		// Fire all output expansion nodes and send offers on all outgoing
		// control flows.

		ExpansionRegion region = (ExpansionRegion) (this.node);

		// *** Send offers from all output expansion nodes concurrently. ***
		ExpansionNodeList outputElements = region.outputElement;
		for (Iterator i = outputElements.iterator(); i.hasNext();) {
			ExpansionNode outputElement = (ExpansionNode) i.next();
			this.getExpansionNodeActivation(outputElement)
					.sendUnofferedTokens();
		}

		// Send offers on all outgoing control flows.
		super.sendOffers();

	} // sendOffers

	public void runGroup(
			fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroup activationGroup) {
		// Set up the inputs for the group with the given index, run the group
		// and then fire the group outputs.

		if (this.isRunning()) {
			Debug.println("[runGroup] groupInput[0] = "
					+ this.inputExpansionTokens.getValue(0).tokens.getValue(
							activationGroup.index - 1).getValue());

			TokenSetList inputTokens = this.inputTokens;
			for (int j = 0; j < inputTokens.size(); j++) {
				TokenSet tokenSet = inputTokens.getValue(j);
				OutputPinActivation regionInput = activationGroup.regionInputs
						.getValue(j);
				regionInput.clearTokens();
				regionInput.addTokens(tokenSet.tokens);
				regionInput.sendUnofferedTokens();
			}

			TokenSetList inputExpansionTokens = this.inputExpansionTokens;
			for (int j = 0; j < inputExpansionTokens.size(); j++) {
				TokenSet tokenSet = inputExpansionTokens.getValue(j);
				OutputPinActivation groupInput = activationGroup.groupInputs
						.getValue(j);
				groupInput.clearTokens();
				if (tokenSet.tokens.size() >= activationGroup.index) {
					groupInput.addToken(tokenSet.tokens
							.getValue(activationGroup.index - 1));
				}
				groupInput.sendUnofferedTokens();
			}

			activationGroup.run(activationGroup.nodeActivations);

			this.terminateGroup(activationGroup);
		}
	} // runGroup

	public void terminateGroup(
			fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroup activationGroup) {
		// Terminate the given activation group, after preserving any group
		// outputs.
		if (this.isRunning() & !this.isSuspended()) {
			OutputPinActivationList groupOutputs = activationGroup.groupOutputs;
			for (int i = 0; i < groupOutputs.size(); i++) {
				OutputPinActivation groupOutput = groupOutputs.getValue(i);
				groupOutput.fire(groupOutput.takeOfferedTokens());
			}

			activationGroup.terminateAll();
		}
	} // terminateGroup

	public fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionNodeActivation getExpansionNodeActivation(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode node) {
		// Return the expansion node activation corresponding to the given
		// expansion node, in the context of the activity node activation group
		// this expansion region activation is in.
		// [Note: Expansion regions do not own their expansion nodes. Instead,
		// they are own as object nodes by the enclosing activity or group.
		// Therefore, they will already be activated along with their expansion
		// region.]

		return (ExpansionNodeActivation) (this.group.getNodeActivation(node));

	} // getExpansionNodeActivation

	public int numberOfValues() {
		// Return the number of values to be acted on by the expansion region of
		// this activation, which is the minimum of the number of values offered
		// to each of the input expansion nodes of the activation.

		ExpansionRegion region = (ExpansionRegion) (this.node);
		ExpansionNodeList inputElements = region.inputElement;

		int n = this.getExpansionNodeActivation(inputElements.getValue(0))
				.countOfferedValues();
		int i = 2;
		while (i <= inputElements.size()) {
			int count = this.getExpansionNodeActivation(
					inputElements.getValue(i - 1)).countOfferedValues();
			if (count < n) {
				n = count;
			}
			i = i + 1;
		}

		return n;
	} // numberOfValues

	public boolean isSuspended() {
		// Check if the activation group for this node is suspended.

		boolean suspended = false;
		int i = 1;
		while (i <= this.activationGroups.size() & !suspended) {
			ActivityNodeActivationGroup group = this.activationGroups
					.get(i - 1);
			suspended = group.isSuspended();
			i = i + 1;
		}

		return suspended;
	} // isSuspended

	public void resume(
			fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroup activationGroup) {
		// Resume an expansion region after the suspension of the given
		// activation group. If the region is iterative, then continue with the
		// iteration. If the region is parallel, and there are no more suspended
		// activation groups, then generate the expansion node output.

		ExpansionRegion region = (ExpansionRegion) this.node;

		this.resume();
		this.terminateGroup(activationGroup);
		if (region.mode == ExpansionKind.iterative) {
			this.runIterative();
		}

		this.doOutput();
	} // resume

} // ExpansionRegionActivation
