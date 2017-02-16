/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import org.modeldriven.fuml.FumlObject;

import fUML.Semantics.Classes.Kernel.Reference;

public abstract class EventOccurrence extends FumlObject {
	
	public Reference target = null;
	
	public void sendTo(Reference target) {
		// Set the target reference and start the SendingBehavior, which
		// will send this event occurrence to the target.
		
		this.target = target;
		_startObjectBehavior();
	}
	
	protected void doSend() {
		// Send this event occurrence to the target.
		
		this.target.send(this);
	}
	
	public abstract boolean match(fUML.Syntax.CommonBehaviors.Communications.Trigger trigger);

	public boolean matchAny(fUML.Syntax.CommonBehaviors.Communications.TriggerList triggers) {
		// Check that at least one of the given triggers is matched by this 
		// event occurrence.
		
		boolean matches = false;
		int i = 1;
		while(!matches & i <= triggers.size()) {
			if(this.match(triggers.get(i-1))) {
				matches = true;
			}
			i = i + 1;
		}
		return matches;
		
	}
	
	public abstract fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList getParameterValues();
	
	private EventOccurrence_SendingBehaviorExecution behavior = new EventOccurrence_SendingBehaviorExecution(this);

	private void _startObjectBehavior() {
		this.behavior._startObjectBehavior();
	}

}
