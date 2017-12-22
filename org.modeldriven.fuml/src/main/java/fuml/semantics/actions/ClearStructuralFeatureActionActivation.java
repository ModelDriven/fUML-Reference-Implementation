
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
import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.structuredclassifiers.Link;
import fuml.semantics.structuredclassifiers.LinkList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.syntax.actions.ClearStructuralFeatureAction;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.structuredclassifiers.Association;

public class ClearStructuralFeatureActionActivation
		extends
		fuml.semantics.actions.StructuralFeatureActionActivation {

	public void doAction() {
		// Get the value of the object input pin.
		// If the given feature is an association end, then
		// destroy all links that have the object input on the opposite end.
		// Otherwise, if the object input is a structured value, then
		// set the appropriate feature of the input value to be empty.

		ClearStructuralFeatureAction action = (ClearStructuralFeatureAction) (this.node);
		StructuralFeature feature = action.structuralFeature;
		Association association = this.getAssociation(feature);

		Value value = this.takeTokens(action.object).getValue(0);

		if (association != null) {
			LinkList links = this.getMatchingLinks(association, feature, value);
			for (int i = 0; i < links.size(); i++) {
				Link link = links.getValue(i);
				link.destroy();
			}
		} else if (value instanceof StructuredValue) {
			// If the value is a data value, then it must be copied before
			// any change is made.
			if (!(value instanceof Reference)) {
				value = value.copy();
			}

			((StructuredValue) value).setFeatureValue(action.structuralFeature,
					new ValueList(), 0);
		}

		if (action.result != null) {
			this.putToken(action.result, value);
		}
	} // doAction

} // ClearStructuralFeatureActionActivation
