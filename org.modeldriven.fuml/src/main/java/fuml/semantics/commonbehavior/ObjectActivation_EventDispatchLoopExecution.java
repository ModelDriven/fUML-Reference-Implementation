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

package fuml.semantics.commonbehavior;

import fuml.semantics.classification.Value;

public class ObjectActivation_EventDispatchLoopExecution extends Execution {

	public ObjectActivation self = null;

	public ObjectActivation_EventDispatchLoopExecution(ObjectActivation self) {
		this.self = self;
	}

	public int signalCount = 0;
	
	public void _startObjectBehavior() {
		// *** This should start the EventDispatchLoop ***

		this.context = self.object;
		
	} // _startObjectBehavior

	public void _send(
			fuml.semantics.commonbehavior.ArrivalSignal signal) {
		// Signal the arrival of a new signal instance in the event pool.
		// *** This should send an ArrivalSignal to the EventDispatchLoop. ***
		
		this.signalCount = this.signalCount + 1;
		if (this.signalCount == 1) {
			ExecutionQueue.enqueue(this);
		}
	} // _send
	
	@Override
	public void execute() {
		this.self.dispatchNextEvent();
		signalCount = signalCount - 1;
		if (this.signalCount > 0) {
			ExecutionQueue.enqueue(this);
		}
	}
	
	@Override
	public Value new_() {
		return new ObjectActivation_EventDispatchLoopExecution(this.self);
	}
	
	@Override
	public String toString() {
		return "EventDispatchLoopExecution(" + this.self.object + ")";
	}

}
