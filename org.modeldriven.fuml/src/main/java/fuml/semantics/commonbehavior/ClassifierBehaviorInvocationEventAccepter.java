
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.semantics.structuredclassifiers.Object_;
import fuml.syntax.commonbehavior.Behavior;

public class ClassifierBehaviorInvocationEventAccepter extends EventAccepter {

	public fuml.semantics.commonbehavior.Execution execution = null;
	public fuml.syntax.structuredclassifiers.Class_ classifier = null;
	public fuml.semantics.commonbehavior.ObjectActivation objectActivation = null;

	public void invokeBehavior(
			fuml.syntax.structuredclassifiers.Class_ classifier,
			fuml.semantics.commonbehavior.ParameterValueList inputs) {
		// Set the classifier for this classifier behavior invocation event accepter 
		// to the given class.
		// If the given class is a behavior, set the execution to be the object
		// of the object activation of the classifier behavior execution.
		// Otherwise the class must be an active class, so get an execution
		// object for the classifier behavior for the class.
		// Set the input parameters for the execution to the given values.
		// Then register this event accepter with the object activation.

		// Debug.println("[invokeBehavior] Invoking behavior for " + classifier.name +
		// "...");

		this.classifier = classifier;
		Object_ object = this.objectActivation.object;

		if (classifier instanceof Behavior) {
			this.execution = (Execution) object;
		} else {
			this.execution = object.locus.factory.createExecution(
					classifier.classifierBehavior, object);
		}

		if (inputs != null) {
			for (int i = 0; i < inputs.size(); i++) {
				ParameterValue input = inputs.getValue(i);
				this.execution.setParameterValue(input);
			}
		}
		
		this.objectActivation.register(this);

	}
	
	public boolean match(EventOccurrence eventOccurrence) {
		// Return true if the given event occurrence is an invocation event
		// occurrence for the execution of this classifier behavior invocation
		// event accepter.
		
		boolean matches = false;
		if (eventOccurrence instanceof InvocationEventOccurrence) {
			matches = ((InvocationEventOccurrence)eventOccurrence).execution == this.execution;
		}
		return matches;
	}
	
	public void accept(EventOccurrence eventOccurrence) {
		// Accept an invocation event occurrence. Execute the execution of this
		// classifier behavior invocation event accepter.
		
		if (eventOccurrence instanceof InvocationEventOccurrence) {
			this.execution.execute();
		}
	}
	
	public void terminate() {
		// Terminate the associated execution.
		// If the execution is not itself the object of the object activation,
		// then destroy it.

		// Debug.println("[terminate] Terminating behavior for " +
		// classifier.name + "...");

		this.execution.terminate();

		if (this.execution != this.objectActivation.object) {
			this.execution.destroy();
		}

	} // terminate

} // ClassifierBehaviorExecution
