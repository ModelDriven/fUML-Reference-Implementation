
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

import fuml.Debug;
import fuml.syntax.actions.ExpansionNode;
import fuml.syntax.actions.ExpansionRegion;

public class ExpansionNodeActivation extends
		fuml.semantics.activities.ObjectNodeActivation {

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
		// Take tokens from all incoming edges.

		Debug.println("[fire] Expansion node " + this.node.name + "...");

		this.addTokens(incomingTokens);

	} // fire

	public void receiveOffer() {
		// Forward the offer on to the expansion region.

		this.getExpansionRegionActivation().receiveOffer();
	} // receiveOffer

	public boolean isReady() {
		// An expansion node is always fired by its expansion region.

		return false;
	} // isReady

	public fuml.semantics.actions.ExpansionRegionActivation getExpansionRegionActivation() {
		// Return the expansion region activation corresponding to this
		// expansion node, in the context of the activity node activation group
		// this expansion node activation is in.

		ExpansionNode node = (ExpansionNode) (this.node);

		ExpansionRegion region = node.regionAsInput;
		if (region == null) {
			region = node.regionAsOutput;
		}

		return (ExpansionRegionActivation) (this.group
				.getNodeActivation(region));

	} // getExpansionRegionActivation

} // ExpansionNodeActivation
