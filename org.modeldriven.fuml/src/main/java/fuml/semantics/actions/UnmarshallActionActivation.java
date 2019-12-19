/*
 * Copyright 2019 Model Driven Solutions, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.values.Value;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.actions.UnmarshallAction;
import fuml.syntax.classification.Classifier;

public class UnmarshallActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		UnmarshallAction action = (UnmarshallAction) this.node;
		Classifier unmarshallType = action.unmarshallType;
		OutputPinList resultPins = action.result;
		
		Value value = this.takeTokens(action.object).getValue(0);
		
		if (value instanceof StructuredValue) {
			FeatureValueList featureValues = ((StructuredValue)value).getMemberValues(unmarshallType);
			for (int i=0; i < featureValues.size(); i++) {
				FeatureValue featureValue = featureValues.getValue(i);
				OutputPin resultPin = resultPins.getValue(i);
				this.putTokens(resultPin, featureValue.values);
			}
		}
	}

}
