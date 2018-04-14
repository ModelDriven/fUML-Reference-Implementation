
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

package fuml.semantics.actions;

import UMLPrimitiveTypes.*;
import fuml.semantics.loci.ChoiceStrategy;
import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;
import fuml.semantics.structuredclassifiers.Link;
import fuml.semantics.structuredclassifiers.LinkList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.syntax.actions.RemoveStructuralFeatureValueAction;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.structuredclassifiers.Association;

public class RemoveStructuralFeatureValueActionActivation
		extends
		fuml.semantics.actions.WriteStructuralFeatureActionActivation {

	public void doAction() {
		// Get the values of the object and value input pins.
		// If the given feature is an association end, then destroy any matching
		// links.
		// Otherwise, if the object input is a structural value, remove values
		// from the given feature.
		// If isRemoveDuplicates is true, then destroy all current matching
		// links or remove all values equal to the input value.
		// If isRemoveDuplicates is false and there is no removeAt input pin,
		// remove any one feature value equal to the input value (if there are
		// any that are equal).
		// If isRemoveDuplicates is false, and there is a removeAt input pin
		// remove the feature value at that position.

		RemoveStructuralFeatureValueAction action = (RemoveStructuralFeatureValueAction) (this.node);
		StructuralFeature feature = action.structuralFeature;
		Association association = this.getAssociation(feature);

		Value value = this.takeTokens(action.object).getValue(0);

		Value inputValue = null;
		if (action.value != null) {
			// NOTE: Multiplicity of the value input pin is required to be 1..1.
			inputValue = this.takeTokens(action.value).getValue(0);
		}

		int removeAt = 0;
		if (action.removeAt != null) {
			removeAt = ((UnlimitedNaturalValue) this
					.takeTokens(action.removeAt).getValue(0)).value.naturalValue;
		}

		if (association != null) {
			LinkList links = this.getMatchingLinksForEndValue(association, feature, value, inputValue);
			
			if (action.isRemoveDuplicates) {
				for (int i = 0; i < links.size(); i++) {
					Link link = links.getValue(i);
					link.destroy();
				}

			} else if (action.removeAt == null) {
				// *** If there is more than one matching link,
				// non-deterministically choose one. ***
				if (links.size() > 0) {
					int i = ((ChoiceStrategy) this.getExecutionLocus().factory
							.getStrategy("choice")).choose(links.size());
					links.getValue(i - 1).destroy();
				}

			} else {
				boolean notFound = true;
				int i = 1;
				while (notFound & i <= links.size()) {
					Link link = links.getValue(i - 1);
					if (link.getFeatureValue(feature).position == removeAt) {
						notFound = false;
						link.destroy();
					}
					i = i + 1;
				}
			}

		} else if (value instanceof StructuredValue) {
			// If the value is a data value, then it must be copied before
			// any change is made.
			if (!(value instanceof Reference)) {
				value = value.copy();
			}

			FeatureValue featureValue = ((StructuredValue) value)
					.getFeatureValue(action.structuralFeature);

			if (action.isRemoveDuplicates) {
				int j = this.position(inputValue, featureValue.values, 1);
				while (j > 0) {
					featureValue.values.remove(j - 1);
					j = this.position(inputValue, featureValue.values, j);
				}

			} else if (action.removeAt == null) {
				intList positions = new intList();
				int j = this.position(inputValue, featureValue.values, 1);
				while (j > 0) {
					positions.addValue(j);
					j = this.position(inputValue, featureValue.values, j + 1);
				}

				if (positions.size() > 0) {
					// *** Nondeterministically choose which value to remove.
					// ***
					int k = ((ChoiceStrategy) this.getExecutionLocus().factory
							.getStrategy("choice")).choose(positions.size());
					featureValue.values.remove(positions.getValue(k - 1) - 1);
				}

			} else {
				if (featureValue.values.size() >= removeAt) {
					featureValue.values.remove(removeAt - 1);
				}
			}
		}

		if (action.result != null) {
			this.putToken(action.result, value);
		}

	} // doAction

} // RemoveStructuralFeatureValueActionActivation
