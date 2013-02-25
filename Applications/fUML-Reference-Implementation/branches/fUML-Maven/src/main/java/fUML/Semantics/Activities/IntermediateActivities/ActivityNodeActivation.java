
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
import fUML.Semantics.Loci.*;

public abstract class ActivityNodeActivation extends
		fUML.Semantics.Loci.LociL1.SemanticVisitor {

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivationGroup group = null;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityNode node = null;
	public fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList incomingEdges = new fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList();
	public fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList outgoingEdges = new fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstanceList();
	public boolean running = false;
	public fUML.Semantics.Activities.IntermediateActivities.TokenList heldTokens = new fUML.Semantics.Activities.IntermediateActivities.TokenList();

	public void run() {
		// Run the activation of this node.

		if (this.node != null) {
			Debug.println("[run] node = " + this.node.name);
		} else {
			Debug.println("[run] Anonymous activation of type "
					+ this.getClass().getName());
		}

		this.running = true;
	} // run

	public void receiveOffer() {
		// Receive an offer from an incoming edge.
		// Check if all prerequisites have been satisfied. If so, fire.

		Debug.println("[receiveOffer] "
				+ (this.node == null ? "..." : "node = " + this.node.name));

		_beginIsolation();

		boolean ready = this.isReady();

		TokenList tokens = new TokenList();
		if (ready) {
			Debug.println("[receiveOffer] Firing.");
			tokens = this.takeOfferedTokens();
		}

		_endIsolation();

		if (ready) {
			this.fire(tokens);
		}

	} // receiveOffer

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeOfferedTokens() {
		// Get tokens from all incoming edges.

		TokenList allTokens = new TokenList();
		ActivityEdgeInstanceList incomingEdges = this.incomingEdges;
		for (int i = 0; i < incomingEdges.size(); i++) {
			ActivityEdgeInstance incomingEdge = incomingEdges.getValue(i);
			TokenList tokens = incomingEdge.takeOfferedTokens();
			for (int j = 0; j < tokens.size(); j++) {
				Token token = tokens.getValue(j);
				allTokens.addValue(token);
			}
		}

		return allTokens;
	} // takeOfferedTokens

	public abstract void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens);

	public void sendOffers(
			fUML.Semantics.Activities.IntermediateActivities.TokenList tokens) {
		// Send offers for the given set of tokens over all outgoing edges (if
		// there are any tokens actually being offered).

		if (tokens.size() > 0) {

			// *** Send all outgoing offers concurrently. ***
			ActivityEdgeInstanceList outgoingEdges = this.outgoingEdges;
			for (Iterator i = outgoingEdges.iterator(); i.hasNext();) {
				ActivityEdgeInstance outgoingEdge = (ActivityEdgeInstance) i
						.next();
				// Debug.println("[sendOffers] Sending offer to " +
				// outgoingEdge.target.node.name + ".");
				outgoingEdge.sendOffer(tokens);
			}

		}

	} // sendOffers

	public void terminate() {
		// Terminate the activation of this node.

		if (this.running) {
			if (this.node != null) {
				Debug.println("[terminate] node = " + this.node.name);
			} else {
				Debug.println("[terminate] Anonymous activation of type "
						+ this.getClass().getName());
			}
		}

		this.running = false;
	} // terminate

	public boolean isReady() {
		// Check if all the prerequisites for this node have been satisfied.
		// By default, check that this node is running.

		return this.isRunning();

	} // isReady

	public boolean isRunning() {
		// Test whether this node activation is running.

		return this.running;
	} // isRunning

	public void addOutgoingEdge(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance edge) {
		// Add an activity edge instance as an outgoing edge of this activity
		// node activation.

		edge.source = this;
		this.outgoingEdges.addValue(edge);
	} // addOutgoingEdge

	public void addIncomingEdge(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance edge) {
		// Add an activity edge instance as an incoming edge of this activity
		// node activation.

		edge.target = this;
		this.incomingEdges.addValue(edge);
	} // addIncomingEdge

	public void createNodeActivations() {
		// Create node activations for any subnodes of the node for this
		// activation.
		// For most kinds of nodes, this does nothing.

		return;

	} // createNodeActivations

	public void createEdgeInstances() {
		// Create edge instances for any edge instances owned by the node for
		// this activation.
		// For most kinds of nodes, this does nothing.

		return;

	} // createEdgeInstances

	public boolean isSourceFor(
			fUML.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance edgeInstance) {
		// Check if this node activation is the effective source for the given
		// edge instance.

		return edgeInstance.source == this;
	} // isSourceFor

	public fUML.Semantics.Activities.IntermediateActivities.ActivityExecution getActivityExecution() {
		// Return the activity execution that contains this activity node
		// activation, directly or indirectly.

		return this.group.getActivityExecution();
	} // getActivityExecution

	public fUML.Semantics.Classes.Kernel.Object_ getExecutionContext() {
		// Get the context object for the containing activity execution.

		return this.getActivityExecution().context;
	} // getExecutionContext

	public fUML.Semantics.Loci.LociL1.Locus getExecutionLocus() {
		// Get the locus of the containing activity execution.

		return this.getActivityExecution().locus;
	} // getExecutionLocus

	public fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation getNodeActivation(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		// Get the activity node activation corresponding to the given activity
		// node, in the context of this activity node activation.
		// By default, return this activity node activation, if it is for the
		// given node, otherwise return nothing.

		ActivityNodeActivation activation = null;
		if (node == this.node) {
			activation = this;
		}

		return activation;
	} // getNodeActivation

	public void addToken(
			fUML.Semantics.Activities.IntermediateActivities.Token token) {
		// Transfer the given token to be held by this node.

		if (this.node == null) {
			Debug.println("[addToken] ...");
		} else {
			Debug.println("[addToken] node = " + this.node.name);
		}

		Token transferredToken = token.transfer(this);
		// Debug.println("[addToken] Adding token with value = " +
		// transferredToken.getValue());
		this.heldTokens.addValue(transferredToken);
	} // addToken

	public int removeToken(
			fUML.Semantics.Activities.IntermediateActivities.Token token) {
		// Remove the given token, if it is held by this node activation.
		// Return the position (counting from 1) of the removed token (0 if
		// there is none removed).

		boolean notFound = true;
		int i = 1;
		while (notFound & i <= this.heldTokens.size()) {
			if (this.heldTokens.getValue(i - 1) == token) {
				if (this.node == null) {
					Debug.println("[removeToken] ...");
				} else {
					Debug.println("[removeToken] node = " + this.node.name);
				}
				this.heldTokens.remove(i - 1);
				notFound = false;
			}
			i = i + 1;
		}

		if (notFound) {
			i = 0;
		} else {
			i = i - 1;
		}

		return i;
	} // removeToken

	public void addTokens(
			fUML.Semantics.Activities.IntermediateActivities.TokenList tokens) {
		// Transfer the given tokens to be the held tokens for this node.

		// if (this.node == null) {
		// Debug.println("[addTokens] ...");
		// } else {
		// Debug.println("[addTokens] node = " + this.node.name);
		// }

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.getValue(i);
			this.addToken(token);
		}
	} // addTokens

	public fUML.Semantics.Activities.IntermediateActivities.TokenList takeTokens() {
		// Take the tokens held by this node activation.

		TokenList tokens = this.getTokens();
		this.clearTokens();

		return tokens;
	} // takeTokens

	public void clearTokens() {
		// Remove all held tokens.

		while (this.heldTokens.size() > 0) {
			this.heldTokens.getValue(0).withdraw();
		}

	} // clearTokens

	public fUML.Semantics.Activities.IntermediateActivities.TokenList getTokens() {
		// Get the tokens held by this node activation.

		// Debug.println("[getTokens] node = " + this.node.name);

		TokenList tokens = new TokenList();
		TokenList heldTokens = this.heldTokens;
		for (int i = 0; i < heldTokens.size(); i++) {
			Token heldToken = heldTokens.getValue(i);
			// Debug.println("[getTokens] token value = " +
			// heldTokens.getValue());
			tokens.addValue(heldToken);
		}

		return tokens;
	} // getTokens

	public void suspend() {
		// Suspend this activation within the activation group that contains it.

		this.group.suspend(this);
	} // suspend

	public void resume() {
		// Resume this activation within the activation group that contains it.

		this.group.resume(this);
	} // resume

} // ActivityNodeActivation
