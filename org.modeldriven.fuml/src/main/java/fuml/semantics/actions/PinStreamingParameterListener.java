/*
 * Copyright 2019 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.activities.ObjectToken;
import fuml.semantics.activities.TokenList;
import fuml.semantics.commonbehavior.StreamingParameterListener;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;

public class PinStreamingParameterListener extends StreamingParameterListener {
	
	public PinActivation nodeActivation = null;

	@Override
	public void post(ValueList values) {
		// Fire the pin activation passing the posted values as incoming tokens,
		// then have the pin activation immediately offer these tokens (since
		// the pin activation would otherwise not offer them until its
		// associated action activation terminates).

		Debug.println("[post] Posting to node " + this.nodeActivation.node.name);
		
		TokenList tokens = new TokenList();
		for (int i = 0; i < values.size(); i++) {
			Value value = values.getValue(i);
			ObjectToken token = new ObjectToken();
			token.value = value;
			tokens.addValue(token);
		}
		
		nodeActivation.fire(tokens);		
		nodeActivation.sendUnofferedTokens();
	}
	
	@Override
	public boolean isTerminated() {
		// This listener is terminated if the node activation is not running.
		
		return !this.nodeActivation.isRunning();
	}

}
