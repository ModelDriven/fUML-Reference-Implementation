/*
 * Copyright 2019-2020 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.semantics.values.ValueList;

public class StreamingParameterValue extends ParameterValue {
	
	public StreamingParameterListener listener;
	
	public void post(ValueList values) {
		// Post the given values to the listener, if there is at
		// least one value.
		
		this.values = values;
		
		if (this.listener != null & values.size() > 0) {
			listener.post(values);
		}
	}
	
	public void register(StreamingParameterListener listener) {
		// Register a listener for this streaming parameter value.
		
		this.listener = listener;
	}
	
	public boolean isTerminated() {
		// Check if this streaming parameter value either has no listener,
		// or it has a listener that has terminated. 
		
		boolean isTerminated = true;
		if (this.listener != null) {
			isTerminated = this.listener.isTerminated();
		}
		return isTerminated;
	}
	
}
