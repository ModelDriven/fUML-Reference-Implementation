/*
 * Copyright 2019-2020 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.actions.UnmarshallAction;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.classification.StructuralFeatureList;
import fuml.syntax.structuredclassifiers.Association;

public class UnmarshallActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		// Get the value from the object input pin. If it is a structured value,
		// get the values for each of its attributes and place them on the
		// corresponding result pin. (Note that the number of result pins is
		// presumed to be the same as the number of attributes.)

		UnmarshallAction action = (UnmarshallAction) this.node;
		Classifier unmarshallType = action.unmarshallType;
		OutputPinList resultPins = action.result;
		
		Value value = this.takeTokens(action.object).getValue(0);
		
		if (value instanceof StructuredValue) {
			StructuralFeatureList features = ((StructuredValue)value).getMemberFeatures(unmarshallType);
			for (int i=0; i < features.size(); i++) {
				StructuralFeature feature = features.getValue(i);
				OutputPin resultPin = resultPins.getValue(i);
				this.putTokens(resultPin, this.getValues(value, feature));
			}
		}
	}

}
