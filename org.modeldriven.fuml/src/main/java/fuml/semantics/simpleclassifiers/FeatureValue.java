
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

package fuml.semantics.simpleclassifiers;

import fuml.semantics.classification.Value;
import fuml.semantics.classification.ValueList;

public class FeatureValue extends org.modeldriven.fuml.FumlObject {

	public fuml.syntax.classification.StructuralFeature feature = null;
	public fuml.semantics.classification.ValueList values = new fuml.semantics.classification.ValueList();
	public int position = 0;

	public boolean hasEqualValues(
			fuml.semantics.simpleclassifiers.FeatureValue other) {
		// Determine if this feature value has an equal set of values as another
		// feature value.
		// If the feature is ordered, then the values also have to be in the
		// same order.

		boolean equal = true;

		if (this.values.size() != other.values.size()) {
			equal = false;

		} else {

			// Debug.println("[hasEqualValues] feature = " + this.feature.name +
			// ", " + this.values.size() + " value(s).");

			if (this.feature.multiplicityElement.isOrdered) {
				int i = 1;
				while (equal & i <= this.values.size()) {
					equal = this.values.getValue(i - 1).equals(
							other.values.getValue(i - 1));
					i = i + 1;
				}

			} else {
				// Note: otherFeatureValues is used here solely as a holder for
				// a copy of the list of other values,
				// since the Java to UML mapping conventions do not allow
				// "remove" on a local list variable.
				FeatureValue otherFeatureValues = new FeatureValue();
				ValueList values = other.values;
				for (int i = 0; i < values.size(); i++) {
					Value value = values.getValue(i);
					otherFeatureValues.values.addValue(value);
				}

				int i = 1;
				while (equal & i <= this.values.size()) {
					// Debug.println("[hasEqualValues] This value [" + (i-1) +
					// "] = " + this.values.getValue(i-1));

					boolean matched = false;
					int j = 1;
					while (!matched & j <= otherFeatureValues.values.size()) {
						if (this.values.getValue(i - 1).equals(
								otherFeatureValues.values.getValue(j - 1))) {
							// Debug.println("[hasEqualValues] Other value [" +
							// (j-1) + "] = " +
							// otherFeatureValues.values.getValue(j-1));
							matched = true;
							otherFeatureValues.values.remove(j - 1);
						}
						j = j + 1;
					}

					equal = matched;
					i = i + 1;
				}
			}
		}

		return equal;
	} // hasEqualValues

	public fuml.semantics.simpleclassifiers.FeatureValue copy() {
		// Create a copy of this feature value.

		FeatureValue newValue = new FeatureValue();

		newValue.feature = this.feature;
		newValue.position = this.position;

		ValueList values = this.values;
		for (int i = 0; i < values.size(); i++) {
			Value value = values.getValue(i);
			newValue.values.addValue(value.copy());
		}

		return newValue;
	} // copy

} // FeatureValue
