
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
import fuml.semantics.classification.ValueList;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.syntax.actions.ReadExtentAction;

public class ReadExtentActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the extent, at the current execution locus, of the classifier
		// (which must be a class) identified in the action.
		// Place references to the resulting set of objects on the result pin.

		ReadExtentAction action = (ReadExtentAction) (this.node);
		ExtensionalValueList objects = this.getExecutionLocus().getExtent(
				action.classifier);

		// Debug.println("[doAction] " + action.classifier.name + " has " +
		// objects.size() + " instance(s).");

		ValueList references = new ValueList();
		for (int i = 0; i < objects.size(); i++) {
			Value object = objects.getValue(i);
			Reference reference = new Reference();
			reference.referent = (Object_) object;
			references.addValue(reference);
		}

		this.putTokens(action.result, references);
	} // doAction

} // ReadExtentActionActivation
