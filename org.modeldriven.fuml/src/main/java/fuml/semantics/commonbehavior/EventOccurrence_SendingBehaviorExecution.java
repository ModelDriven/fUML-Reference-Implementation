/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.semantics.values.Value;

public class EventOccurrence_SendingBehaviorExecution extends Execution {
	
	public EventOccurrence self = null;
	
	public EventOccurrence_SendingBehaviorExecution(EventOccurrence self) {
		this.self = self;
	}
	
	public void _startObjectBehavior() {
		this.context = self.target.referent;
		ExecutionQueue.enqueue(this);
	}
	
	@Override
	public void execute() {
		this.self.doSend();
	}

	@Override
	public Value new_() {
		return new EventOccurrence_SendingBehaviorExecution(this.self);
	}
	
	@Override
	public String toString() {
		return "SendingBehaviorExecution(" + this.self + ")";
	}

}
