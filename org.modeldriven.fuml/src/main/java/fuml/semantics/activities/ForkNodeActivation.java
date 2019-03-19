
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;

public class ForkNodeActivation extends
		fuml.semantics.activities.ControlNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// Create forked tokens for all incoming tokens and offer them on all
		// outgoing edges.

		Debug.println(this.node == null? "[fire] Anonymous fork node.": 
				"[fire] Fork node " + this.node.name + "...");

		ActivityEdgeInstanceList outgoingEdges = this.outgoingEdges;
		int outgoingEdgeCount = outgoingEdges.size();

		TokenList forkedTokens = new TokenList();
		for (int i = 0; i < incomingTokens.size(); i++) {
			Token token = incomingTokens.getValue(i);
			ForkedToken forkedToken = new ForkedToken();
			forkedToken.baseToken = token;
			forkedToken.remainingOffersCount = outgoingEdgeCount;
			forkedToken.baseTokenIsWithdrawn = false;
			forkedTokens.addValue(forkedToken);
		}

		this.addTokens(forkedTokens);

		this.sendOffers(forkedTokens);
	} // fire

	public void terminate() {
		// Terminate and remove any offered tokens.

		super.terminate();
		this.clearTokens();
	} // terminate

} // ForkNodeActivation
