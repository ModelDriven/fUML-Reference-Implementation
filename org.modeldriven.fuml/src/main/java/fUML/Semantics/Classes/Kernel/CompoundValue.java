
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

package fUML.Semantics.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.*;
import fUML.Semantics.Loci.*;

public abstract class CompoundValue extends
		fUML.Semantics.Classes.Kernel.StructuredValue {

	public fUML.Semantics.Classes.Kernel.FeatureValueList featureValues = new fUML.Semantics.Classes.Kernel.FeatureValueList();

	public boolean equals(fUML.Semantics.Classes.Kernel.Value otherValue) {
		// Test if this data value is equal to the otherValue.
		// To be equal, the otherValue must also be a compund value with the
		// same types and equal values for each feature.

		// Debug.println("[equals] othervalue instanceof CompoundValue = " +
		// (otherValue instanceof CompoundValue));
		// Debug.println("[equals] super.equals(otherValue) = " +
		// super.equals(otherValue));

		boolean isEqual = otherValue instanceof CompoundValue;

		if (isEqual) {

			CompoundValue otherCompoundValue = (CompoundValue) otherValue;
			// Debug.println("[equals] " + this.featureValues.size() +
			// " feature(s).");

			isEqual = super.equals(otherValue)
					& otherCompoundValue.featureValues.size() == this.featureValues
							.size();

			int i = 1;
			while (isEqual & i <= this.featureValues.size()) {
				FeatureValue thisFeatureValue = this.featureValues
						.getValue(i - 1);

				boolean matched = false;
				int j = 1;
				while (!matched & j <= otherCompoundValue.featureValues.size()) {
					FeatureValue otherFeatureValue = otherCompoundValue.featureValues
							.getValue(j - 1);
					if (thisFeatureValue.feature == otherFeatureValue.feature) {
						matched = thisFeatureValue
								.hasEqualValues(otherFeatureValue);
					}
					j = j + 1;
				}

				isEqual = matched;
				i = i + 1;
			}
		}

		return isEqual;
	} // equals

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new data value with the same featureValues as this data
		// value.

		CompoundValue newValue = (CompoundValue) (super.copy());

		FeatureValueList featureValues = this.featureValues;
		for (int i = 0; i < featureValues.size(); i++) {
			FeatureValue featureValue = featureValues.getValue(i);
			newValue.featureValues.addValue(featureValue.copy());
		}

		return newValue;
	} // copy

	public fUML.Semantics.Classes.Kernel.FeatureValue getFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature) {
		// Get the value(s) of the member of featureValues for the given
		// feature.

		FeatureValue featureValue = null;
		int i = 1;
		while (featureValue == null & i <= this.featureValues.size()) {
			if (this.featureValues.getValue(i - 1).feature == feature) {
				featureValue = this.featureValues.getValue(i - 1);
			}
			i = i + 1;
		}

		return featureValue;
	} // getFeatureValue

	public void setFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature,
			fUML.Semantics.Classes.Kernel.ValueList values, int position) {
		// Set the value(s) of the member of featureValues for the given
		// feature.

		FeatureValue featureValue = this.getFeatureValue(feature);

		if (featureValue == null) {
			featureValue = new FeatureValue();
			this.featureValues.addValue(featureValue);
		}

		featureValue.feature = feature;
		featureValue.values = values;
		featureValue.position = position;

	} // setFeatureValue

	public fUML.Semantics.Classes.Kernel.FeatureValueList getFeatureValues() {
		// Return the feature values for this compound value.

		return this.featureValues;

	} // getFeatureValues

	public void removeFeatureValues(
			fUML.Syntax.Classes.Kernel.Classifier classifier) {
		// Remove all feature values for features whose type is the given
		// classifier.

		int i = 1;
		while (i <= this.featureValues.size()) {
			if (this.featureValues.getValue(i - 1).feature.typedElement.type == classifier) {
				this.featureValues.remove(i - 1);
			} else {
				i = i + 1;
			}
		}
	} // removeFeatureValues

	public String toString() {
		String buffer = "(" + this.objectId() + ":";

		ClassifierList types = this.getTypes();

		int i = 1;
		while (i <= types.size()) {
			buffer = buffer + " " + types.getValue(i - 1).name;
			i = i + 1;
		}

		int k = 1;
		while (k <= this.featureValues.size()) {
			FeatureValue featureValue = this.featureValues.getValue(k - 1);
			buffer = buffer + "\n\t\t" + featureValue.feature.name + "["
					+ featureValue.position + "]  =";

			int j = 1;
			while (j <= featureValue.values.size()) {
				buffer = buffer + " "
						+ featureValue.values.getValue(j - 1).toString();
				j = j + 1;
			}

			k = k + 1;
		}

		return buffer + ")";
	} // toString

} // CompoundValue
