/*
 * Copyright 2020 Model Driven Solutions, Inc.
 * Copyright 2020 CEA LIST.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.values.Value;
import fuml.syntax.actions.RaiseExceptionAction;

public class RaiseExceptionActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		// Get the value on the exception pin and propagate it as an exception.
		
		RaiseExceptionAction action = (RaiseExceptionAction)this.node;
		Value exception = this.takeTokens(action.exception).getValue(0);
		
		Debug.println("[doAction] action = " + action.name + ", exception = " + exception);
		
		this.propagateException(exception);
	}

}
