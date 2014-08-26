
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

package fUML.Semantics.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.LociL1.*;

public class AddStructuralFeatureValueActionActivation
		extends
		fUML.Semantics.Actions.IntermediateActions.WriteStructuralFeatureActionActivation {

	public void doAction() {
		// Get the values of the object and value input pins.
		// If the given feature is an association end, then create a link
		// between the object and value inputs.
		// Otherwise, if the object input is a structural value, then add a
		// value to the values for the feature.
		// If isReplaceAll is true, first remove all current matching links or
		// feature values.
		// If isReplaceAll is false and there is an insertAt pin, insert the
		// value at the appropriate position.

		AddStructuralFeatureValueAction action = (AddStructuralFeatureValueAction) (this.node);
		StructuralFeature feature = action.structuralFeature;
		Association association = this.getAssociation(feature);

		Value value = this.takeTokens(action.object).getValue(0);
		ValueList inputValues = this.takeTokens(action.value);

		// NOTE: Multiplicity of the value input pin is required to be 1..1.
		Value inputValue = inputValues.getValue(0);

		int insertAt = 0;
		if (action.insertAt != null) {
			insertAt = ((UnlimitedNaturalValue) this
					.takeTokens(action.insertAt).getValue(0)).value.naturalValue;
		}

		if (association != null) {
			LinkList links = this.getMatchingLinks(association, feature, value);

			Property oppositeEnd = this.getOppositeEnd(association, feature);
			int position = 0;
			if (oppositeEnd.multiplicityElement.isOrdered) {
				position = this.getMatchingLinks(association, oppositeEnd,
						inputValue).size() + 1;
			}

			if (action.isReplaceAll) {
				for (int i = 0; i < links.size(); i++) {
					Link link = links.getValue(i);
					link.destroy();
				}
			} else if (feature.multiplicityElement.isUnique) {
				int i = 1;
				boolean destroyed = false;
				while (!destroyed & i <= links.size()) {
					Link link = links.getValue(i - 1);
					FeatureValue featureValue = link.getFeatureValue(feature);
					if (featureValue.values.getValue(0).equals(inputValue)) {
						position = link.getFeatureValue(oppositeEnd).position;
						link.destroy();
						destroyed = true;
					}
					i = i + 1;
				}
			}

			Link newLink = new Link();
			newLink.type = association;

			newLink.setFeatureValue(feature, inputValues, insertAt);

			ValueList oppositeValues = new ValueList();
			oppositeValues.addValue(value);
			newLink.setFeatureValue(oppositeEnd, oppositeValues, position);

			newLink.addTo(this.getExecutionLocus());

		} else if (value instanceof StructuredValue) {
			// If the value is a data value, then it must be copied before
			// any change is made.
			if (!(value instanceof Reference)) {
				value = value.copy();
			}

			StructuredValue structuredValue = (StructuredValue) value;

			if (action.isReplaceAll) {
				structuredValue.setFeatureValue(feature, inputValues, 0);
			} else {
				FeatureValue featureValue = structuredValue
						.getFeatureValue(feature);

				if (featureValue.values.size() > 0 & insertAt == 0) {
					// *** If there is no insertAt pin, then the structural
					// feature must be unordered, and the insertion position is
					// immaterial. ***
					insertAt = ((ChoiceStrategy) this.getExecutionLocus().factory
							.getStrategy("choice")).choose(featureValue.values
							.size());
				}

				if (feature.multiplicityElement.isUnique) {
					// Remove any existing value that duplicates the input value
					int j = position(inputValue, featureValue.values, 1);
					if (j > 0) {
						featureValue.values.remove(j - 1);
					}
				}

				if (insertAt <= 0) { // Note: insertAt = -1 indicates an
										// unlimited value of "*"
					featureValue.values.addValue(inputValue);
				} else {
					featureValue.values.addValue(insertAt - 1, inputValue);
				}
			}
		}

		if (action.result != null) {
			this.putToken(action.result, value);
		}

	} // doAction

} // AddStructuralFeatureValueActionActivation
