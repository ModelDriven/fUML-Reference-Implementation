
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.semantics.values.Value;
import fuml.syntax.actions.ReadStructuralFeatureAction;
import fuml.syntax.classification.StructuralFeature;

public class ReadStructuralFeatureActionActivation extends fuml.semantics.actions.StructuralFeatureActionActivation {

	public void doAction() {
		// Get the value of the object input pin.
		// If the given feature is an association end, then get all values of
		// that end for which the opposite end has the object input value and 
		// place them on the result pin.
		// Otherwise, if the object input value is a structured value, then get
		// the values of the appropriate feature of the input value and place 
		// them on the result output pin.

		ReadStructuralFeatureAction action = (ReadStructuralFeatureAction) (this.node);
		StructuralFeature feature = action.structuralFeature;

		Value value = this.takeTokens(action.object).getValue(0);
		this.putTokens(action.result, this.getValues(value, feature));
	} // doAction

} // ReadStructuralFeatureActionActivation
