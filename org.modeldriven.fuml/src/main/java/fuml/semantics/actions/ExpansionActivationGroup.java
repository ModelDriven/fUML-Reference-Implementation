
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

package fuml.semantics.actions;

import fuml.semantics.activities.ActivityNodeActivation;
import fuml.syntax.actions.ExpansionRegion;
import fuml.syntax.actions.InputPinList;

public class ExpansionActivationGroup
		extends
		fuml.semantics.activities.ActivityNodeActivationGroup {

	public fuml.semantics.actions.OutputPinActivationList regionInputs = new fuml.semantics.actions.OutputPinActivationList();
	public fuml.semantics.actions.OutputPinActivationList groupInputs = new fuml.semantics.actions.OutputPinActivationList();
	public fuml.semantics.actions.ExpansionRegionActivation regionActivation = null;
	public fuml.semantics.actions.OutputPinActivationList groupOutputs = new fuml.semantics.actions.OutputPinActivationList();
	public int index = 0;

	public fuml.semantics.activities.ActivityNodeActivation getNodeActivation(
			fuml.syntax.activities.ActivityNode node) {
		// If the given node is an input pin of the expansion region, then
		// return the corresponding region-input output-pin activation.
		// If the given node is an input expansion node of the expansion region,
		// then return the corresponding group-input output-pin activation.
		// If the given node is an output expansion node of the expansion
		// region, then return the corresponding group-output output-pin
		// activation.
		// Otherwise return the node activation from the activation group, as
		// usual.

		ExpansionRegion region = (ExpansionRegion) (this.regionActivation.node);

		InputPinList inputs = region.input;
		ActivityNodeActivation activation = null;

		int i = 1;
		while (activation == null & i <= region.input.size()) {
			if (node == region.input.getValue(i - 1)) {
				activation = this.regionInputs.getValue(i - 1);
			}
			i = i + 1;
		}

		int j = 1;
		while (activation == null & j <= region.inputElement.size()) {
			if (node == region.inputElement.getValue(j - 1)) {
				activation = this.groupInputs.getValue(j - 1);
			}
			j = j + 1;
		}

		int k = 1;
		while (activation == null & k <= region.outputElement.size()) {
			if (node == region.outputElement.getValue(k - 1)) {
				activation = this.groupOutputs.getValue(k - 1);
			}
			k = k + 1;
		}

		if (activation == null) {
			activation = super.getNodeActivation(node);
		}

		return activation;
	} // getNodeActivation

	public fuml.semantics.activities.ActivityExecution getActivityExecution() {
		// Get the activity execution that contains the expansion region
		// activation for this activation group.

		return this.regionActivation.getActivityExecution();
	} // getActivityExecution

	public void suspend(
			fuml.semantics.activities.ActivityNodeActivation activation) {
		// Suspend the given activation in this activation group. If this is
		// the only suspended activation, then suspend the associated region
		// activation.

		if (!this.isSuspended()) {
			this.regionActivation.suspend();
		}
		super.suspend(activation);
	} // suspend

	public void resume(
			fuml.semantics.activities.ActivityNodeActivation activation) {
		// Resume the given activation in this activation group. If this is the
		// last suspended activation, then resume the associated region
		// activation.

		super.resume(activation);
		if (!this.isSuspended()) {
			this.regionActivation.resume(this);
		}
	} // resume

} // ExpansionActivationGroup
