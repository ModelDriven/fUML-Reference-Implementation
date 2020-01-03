/*
 * Copyright 2019 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;
import fuml.semantics.commonbehavior.StreamingParameterListener;
import fuml.semantics.values.ValueList;

public class ActivityParameterNodeStreamingParameterListener extends StreamingParameterListener {
	
	public ActivityParameterNodeActivation nodeActivation = null;

	@Override
	public void post(ValueList values) {
		// Fire the activity parameter node activation.
		// (Note that the values do not have to be passed to the node activation,
		// because an input activity parameter node activation retrieves values
		// directly from the relevant parameter value.)
		
		Debug.println("[post] Posting to node " + this.nodeActivation.node.name);
		
		nodeActivation.fire(new TokenList());	
	}
	
	@Override
	public boolean isTerminated() {
		// This listener is terminated if the node activation is not running.
		
		return !this.nodeActivation.isRunning();
	}

}
