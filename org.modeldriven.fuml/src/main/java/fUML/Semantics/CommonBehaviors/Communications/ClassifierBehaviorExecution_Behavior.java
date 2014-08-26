
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

package fUML.Semantics.CommonBehaviors.Communications;

public class ClassifierBehaviorExecution_Behavior {

	public ClassifierBehaviorExecution self = null;

	public ClassifierBehaviorExecution_Behavior(ClassifierBehaviorExecution self) {
		this.self = self;
	}

	public void _startObjectBehavior() {
		// *** This should start the asynchronous ClassifierBehaviorExecutionActivity to do the following. ***
		// this.self.execution.execute();
		ExecutionQueue.enqueue(this.self.execution);
	}

}
