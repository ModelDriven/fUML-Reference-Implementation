/*
 * Copyright 2019 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.semantics.values.ValueList;

public abstract class StreamingParameterListener {
	
	public abstract void post(ValueList values);
	
	public abstract boolean isTerminated();

}
