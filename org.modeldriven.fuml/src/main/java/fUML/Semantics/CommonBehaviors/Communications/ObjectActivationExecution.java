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

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;

public class ObjectActivationExecution extends Execution {
	
	private ObjectActivation_Behavior behavior = null;
	
	public ObjectActivationExecution(ObjectActivation_Behavior activation) {
		this.behavior = activation;
		this.context = this.behavior.self.object;
	}

	@Override
	public void execute() {
		this.behavior.dispatchNextEvent();
	}

	@Override
	public Value new_() {
		return new ObjectActivationExecution(this.behavior);
	}

}
