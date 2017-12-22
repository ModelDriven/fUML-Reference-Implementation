/*
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */
package fuml.semantics.activities;

import fuml.Debug;

public class CentralBufferNodeActivation extends ObjectNodeActivation {

	@Override
	public void fire(TokenList incomingTokens) {
		// Add all incoming tokens to the central buffer node.
		// Offer any tokens that have not yet been offered.
		
		Debug.println("[fire] " + this.node.getClass().getSimpleName() + " " + this.node.name);

		this.addTokens(incomingTokens);
		this.sendUnofferedTokens();
	}

}
