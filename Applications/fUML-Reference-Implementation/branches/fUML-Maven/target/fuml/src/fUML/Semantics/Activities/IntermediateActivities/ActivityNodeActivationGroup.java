
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

import java.util.Iterator;

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
import fUML.Semantics.Activities.CompleteStructuredActivities.*;
import fUML.Semantics.Loci.*;

public class ActivityNodeActivationGroup extends
		org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList edgeInstances = new fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList();
	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList nodeActivations = new fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList();
	public fUML.Semantics.Activities.IntermediateActivities.ActivityExecution activityExecution = null;
	public fUML.Semantics.Activities.CompleteStructuredActivities.StructuredActivityNodeActivation containingNodeActivation = null;
	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList suspendedActivations = new fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList();

	public void run(
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList activations) {
		// Run the given node activations and then (concurrently) send an offer
		// to all activations for nodes with no incoming edges within the given
		// set.

		for (int i = 0; i < activations.size(); i++) {
			ActivityNodeActivation activation = activations.getValue(i);
			activation.run();
		}

		Debug.println("[run] Checking for enabled nodes...");

		ActivityNodeActivationList enabledActivations = new ActivityNodeActivationList();

		for (int i = 0; i < activations.size(); i++) {
			ActivityNodeActivation activation = activations.getValue(i);

			Debug
					.println("[run] Checking node " + activation.node.name
							+ "...");

			if (activation instanceof ActionActivation
					| activation instanceof ControlNodeActivation
					| activation instanceof ActivityParameterNodeActivation) {

				boolean isEnabled = this.checkIncomingEdges(
						activation.incomingEdges, activations);

				// For an action activation, also consider incoming edges to
				// input pins
				if (isEnabled & activation instanceof ActionActivation) {
					InputPinList inputPins = ((Action) activation.node).input;
					int j = 1;
					while (j <= inputPins.size() & isEnabled) {
						InputPin inputPin = inputPins.getValue(j - 1);
						ActivityEdgeInstanceList inputEdges = ((ActionActivation) activation)
								.getPinActivation(inputPin).incomingEdges;
						isEnabled = this.checkIncomingEdges(inputEdges,
								activations);
						j = j + 1;
					}
				}

				if (isEnabled) {
					Debug.println("[run] Node " + activation.node.name
							+ " is enabled.");
					enabledActivations.addValue(activation);
				}
			}
		}

		// Debug.println("[run] " + enabledActivations.size() +
		// " node(s) are enabled.");

		// *** Send offers to all enabled nodes concurrently. ***
		for (Iterator i = enabledActivations.iterator(); i.hasNext();) {
			ActivityNodeActivation activation = (ActivityNodeActivation) i
					.next();
			Debug.println("[run] Sending offer to node " + activation.node.name
					+ ".");
			activation.receiveOffer();
		}
	} // run

	public boolean checkIncomingEdges(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList incomingEdges,
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationList activations) {
		// Check if any incoming edges have a source in a given set of
		// activations.

		int j = 1;
		boolean notFound = true;

		while (j <= incomingEdges.size() & notFound) {
			int k = 1;
			while (k <= activations.size() & notFound) {
				if (activations.getValue(k - 1).isSourceFor(
						incomingEdges.getValue(j - 1))) {
					notFound = false;
				}
				k = k + 1;
			}
			j = j + 1;
		}

		return notFound;
	} // checkIncomingEdges

	public void runNodes(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList nodes) {
		// Run the node activations associated with the given nodes in this
		// activation group.

		ActivityNodeActivationList nodeActivations = new ActivityNodeActivationList();

		for (int i = 0; i < nodes.size(); i++) {
			ActivityNode node = nodes.getValue(i);
			ActivityNodeActivation nodeActivation = this
					.getNodeActivation(node);
			if (nodeActivation != null) {
				nodeActivations.addValue(nodeActivation);
			}
		}

		this.run(nodeActivations);
	} // runNodes

	public void activate(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList nodes,
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList edges) {
		// Activate and run the given set of nodes with the given set of edges,
		// within this activation group.

		this.createNodeActivations(nodes);
		this.createEdgeInstances(edges);
		this.run(this.nodeActivations);

		// Debug.println("[activate] Exiting.");
	} // activate

	public void terminateAll() {
		// Terminate all node activations in the group.

		Debug.println("[terminateAll] Terminating activation group for "
				+ (this.activityExecution != null ? "activity "
						+ this.activityExecution.getTypes().getValue(0).name
						: this.containingNodeActivation != null ? "node "
								+ this.containingNodeActivation.node.name
								: "expansion region") + ".");

		ActivityNodeActivationList nodeActivations = this.nodeActivations;
		for (int i = 0; i < nodeActivations.size(); i++) {
			ActivityNodeActivation nodeActivation = nodeActivations.getValue(i);
			nodeActivation.terminate();
		}

		this.suspendedActivations.clear();
	} // terminateAll

	public void createNodeActivations(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList nodes) {
		// Add activity node activations for the given set of nodes to this
		// group and create edge instances between them.

		for (int i = 0; i < nodes.size(); i++) {
			ActivityNode node = nodes.getValue(i);

			Debug
					.println("[createNodeActivations] Creating a node activation for "
							+ node.name + "...");
			this.createNodeActivation(node);

		}

	} // createNodeActivations

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation createNodeActivation(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		// Create an activity node activation for a given activity node in this
		// activity node activation group.

		ActivityNodeActivation activation = (ActivityNodeActivation) (this
				.getActivityExecution().locus.factory.instantiateVisitor(node));
		activation.node = node;
		activation.running = false;

		this.addNodeActivation(activation);

		activation.createNodeActivations();

		return activation;
	} // createNodeActivation

	public void addNodeActivation(
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation activation) {
		// Add the given node activation to this group.

		activation.group = this;
		this.nodeActivations.addValue(activation);
	} // addNodeActivation

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation getNodeActivation(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		// Return the node activation (if any) in this group,
		// or any nested group, corresponding to the given activity node.
		// If this is a group for a structured activity node activation,
		// also include the pin activations for that node activation.

		ActivityNodeActivation activation = null;

		if (this.containingNodeActivation != null && node instanceof Pin) {
			activation = this.containingNodeActivation
					.getPinActivation((Pin) node);
		}

		if (activation == null) {
			int i = 1;
			while (activation == null & i <= this.nodeActivations.size()) {
				activation = this.nodeActivations.getValue(i - 1)
						.getNodeActivation(node);
				i = i + 1;
			}
		}

		return activation;
	} // getNodeActivation

	public void createEdgeInstances(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList edges) {
		// Create instance edges for the given activity edges, as well as for
		// edge instances within any nodes activated in this group.

		for (int i = 0; i < edges.size(); i++) {
			ActivityEdge edge = edges.getValue(i);

			Debug
					.println("[createEdgeInstances] Creating an edge instance from "
							+ edge.source.name
							+ " to "
							+ edge.target.name
							+ ".");

			ActivityEdgeInstance edgeInstance = new ActivityEdgeInstance();
			edgeInstance.edge = edge;

			this.addEdgeInstance(edgeInstance);
			this.getNodeActivation(edge.source).addOutgoingEdge(edgeInstance);
			this.getNodeActivation(edge.target).addIncomingEdge(edgeInstance);

			// Debug.println("[createEdgeInstances] Edge instance created...");
		}

		ActivityNodeActivationList nodeActivations = this.nodeActivations;
		for (int i = 0; i < nodeActivations.size(); i++) {
			ActivityNodeActivation nodeActivation = nodeActivations.getValue(i);
			nodeActivation.createEdgeInstances();
		}

		// Debug.println("[createEdgeInstances] Done creating edge instances.");
	} // createEdgeInstances

	public void addEdgeInstance(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance instance) {
		// Add the given edge instance to this group.

		instance.group = this;
		this.edgeInstances.addValue(instance);
	} // addEdgeInstance

	public fUML.Semantics.Activities.IntermediateActivities.ActivityExecution getActivityExecution() {
		// Return the activity execution to which this group belongs, directly
		// or indirectly.

		ActivityExecution activityExecution = this.activityExecution;
		if (activityExecution == null) {
			activityExecution = this.containingNodeActivation.group
					.getActivityExecution();
		}

		// Debug.println("[getActivityExecution] activityExecution = " +
		// activityExecution);

		return activityExecution;
	} // getActivityExecution

	public fUML.Semantics.Activities.IntermediateActivities.ActivityParameterNodeActivationList getOutputParameterNodeActivations() {
		// Return the set of all activations in this group of activity parameter
		// nodes for output (inout, out and return) parameters.

		ActivityParameterNodeActivationList parameterNodeActivations = new ActivityParameterNodeActivationList();
		ActivityNodeActivationList nodeActivations = this.nodeActivations;
		for (int i = 0; i < nodeActivations.size(); i++) {
			ActivityNodeActivation activation = nodeActivations.getValue(i);
			if (activation instanceof ActivityParameterNodeActivation) {
				if (activation.incomingEdges.size() > 0) {
					parameterNodeActivations
							.addValue((ActivityParameterNodeActivation) activation);
				}
			}
		}

		return parameterNodeActivations;
	} // getOutputParameterNodeActivations

	public boolean hasSourceFor(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance edgeInstance) {
		// Returns true if this activation group has a node activation
		// corresponding to the source of the given edge instance.

		boolean hasSource = false;
		ActivityNodeActivationList activations = this.nodeActivations;
		int i = 1;
		while (!hasSource & i <= activations.size()) {
			hasSource = activations.getValue(i - 1).isSourceFor(edgeInstance);
			i = i + 1;
		}
		return hasSource;
	} // hasSourceFor

	public boolean isSuspended() {
		// Check if this activitation group has any suspended activations and
		// is,
		// therefore, itself suspended.

		return this.suspendedActivations.size() > 0;
	} // isSuspended

	public void suspend(
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation activation) {
		// Suspend the given activation in this activation group. If this is
		// the only suspended activation, and the activation group has a
		// containing node activation, then suspend that containing activation.

		Debug.println("[suspend] node="
				+ (activation.node == null ? "null" : activation.node.name));

		if (!this.isSuspended()) {
			StructuredActivityNodeActivation containingNodeActivation = this.containingNodeActivation;
			if (containingNodeActivation != null) {
				containingNodeActivation.suspend();
			}
		}
		this.suspendedActivations.addValue(activation);
	} // suspend

	public void resume(
			fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation activation) {
		// Resume the given activation by removing it from the suspended
		// activation list for this activation group. If this is the last
		// suspended activation, and the activation group has a containing
		// node activation, then resume that containing activation.

		Debug.println("[resume] node="
				+ (activation.node == null ? "null" : activation.node.name));

		boolean found = false;
		int i = 1;
		while (!found & i <= this.suspendedActivations.size()) {
			if (this.suspendedActivations.get(i - 1) == activation) {
				this.suspendedActivations.removeValue(i - 1);
				found = true;
			}
			i = i + 1;
		}
		if (!this.isSuspended()) {
			StructuredActivityNodeActivation containingNodeActivation = this.containingNodeActivation;
			if (containingNodeActivation != null) {
				containingNodeActivation.resume();
			}
		}
	} // resume

} // ActivityNodeActivationGroup
