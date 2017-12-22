
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

package fuml.semantics.actions;

import fuml.semantics.classification.Value;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.syntax.actions.StartClassifierBehaviorAction;
import fuml.syntax.structuredclassifiers.Class_;

public class StartClassifierBehaviorActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the value on the object input pin. If it is not a reference, then
		// do nothing.
		// Start the classifier behavior of the referent object for the
		// classifier given as the type of the object input pin.
		// If the object input pin has no type, then start the classifier
		// behaviors of all types of the referent object. [The required behavior
		// in this case is not clear from the spec.]

		StartClassifierBehaviorAction action = (StartClassifierBehaviorAction) (this.node);

		Value object = this.takeTokens(action.object).getValue(0);

		if (object instanceof Reference) {
			((Reference) object).startBehavior(
					(Class_) (action.object.typedElement.type),
					new ParameterValueList());
		}
	} // doAction

} // StartClassifierBehaviorActionActivation
