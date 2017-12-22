
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
import fuml.syntax.actions.ReadStructuralFeatureAction;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.structuredclassifiers.Association;

public class ReadStructuralFeatureActionActivation
		extends
		fuml.semantics.actions.StructuralFeatureActionActivation {

	public void doAction() {
		// Get the value of the object input pin.
		// If the given feature is an association end, then get all values of
		// the that end.
		// for which the opposite end has the object input value and place them
		// on the result pin.
		// Otherwise, if the object input value is a structural value, then get
		// the values
		// of the appropriate feature of the input value and place them on the
		// result output pin.

		ReadStructuralFeatureAction action = (ReadStructuralFeatureAction) (this.node);
		StructuralFeature feature = action.structuralFeature;
		Association association = this.getAssociation(feature);

		Value value = this.takeTokens(action.object).getValue(0);
		ValueList resultValues = new ValueList();

		if (association != null) {
			LinkList links = this.getMatchingLinks(association, feature, value);
			for (int i = 0; i < links.size(); i++) {
				Link link = links.getValue(i);
				resultValues.addValue(link.getFeatureValue(feature).values
						.getValue(0));
			}
		} else if (value instanceof StructuredValue) {
			// Debug.println("[ReadStructuralFeatureActionActivation] value = "
			// + value +", structural feature = " + feature.name);
			resultValues = ((StructuredValue) value).getFeatureValue(feature).values;
		}

		this.putTokens(action.result, resultValues);
	} // doAction

} // ReadStructuralFeatureActionActivation
