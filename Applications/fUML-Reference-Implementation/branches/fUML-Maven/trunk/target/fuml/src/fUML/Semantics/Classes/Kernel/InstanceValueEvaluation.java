
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
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.*;
import fUML.Semantics.Loci.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public class InstanceValueEvaluation extends
		fUML.Semantics.Classes.Kernel.Evaluation {

	public fUML.Semantics.Classes.Kernel.Value evaluate() {
		// If the instance specification is for an enumeration, then return the
		// identified enumeration literal.
		// If the instance specification is for a data type (but not a primitive
		// value or an enumeration), then create a data value of the given data
		// type.
		// If the instance specification is for an object, then create an object
		// at the current locus with the specified types.
		// Set each feature of the created value to the result of evaluating the
		// value specifications for the specified slot for the feature.

		// Debug.println("[evaluate] InstanceValueEvaluation...");

		InstanceSpecification instance = ((InstanceValue) this.specification).instance;
		ClassifierList types = instance.classifier;
		Classifier myType = types.getValue(0);

		Debug.println("[evaluate] type = " + myType.name);

		Value value;
		if (instance instanceof EnumerationLiteral) {
			// Debug.println("[evaluate] Type is an enumeration.");
			EnumerationValue enumerationValue = new EnumerationValue();
			enumerationValue.type = (Enumeration) myType;
			enumerationValue.literal = (EnumerationLiteral) instance;
			value = enumerationValue;
		} else {
			StructuredValue structuredValue = null;

			if (myType instanceof DataType) {
				// Debug.println("[evaluate] Type is a data type.");
				DataValue dataValue = new DataValue();
				dataValue.type = (DataType) myType;
				structuredValue = dataValue;
			} else {
				Object_ object = null;
				if (myType instanceof Behavior) {
					// Debug.println("[evaluate] Type is a behavior.");
					object = this.locus.factory.createExecution(
							(Behavior) myType, null);
				} else {
					// Debug.println("[evaluate] Type is a class.");
					object = new Object_();
					for (int i = 0; i < types.size(); i++) {
						Classifier type = types.getValue(i);
						object.types.addValue((Class_) type);
					}
				}

				this.locus.add(object);

				Reference reference = new Reference();
				reference.referent = object;
				structuredValue = reference;
			}

			structuredValue.createFeatureValues();

			// Debug.println("[evaluate] " + instance.slot.size() +
			// " slot(s).");

			SlotList instanceSlots = instance.slot;
			for (int i = 0; i < instanceSlots.size(); i++) {
				Slot slot = instanceSlots.getValue(i);
				ValueList values = new ValueList();

				// Debug.println("[evaluate] feature = " +
				// slot.definingFeature.name + ", " + slot.value.size() +
				// " value(s).");
				ValueSpecificationList slotValues = slot.value;
				for (int j = 0; j < slotValues.size(); j++) {
					ValueSpecification slotValue = slotValues.getValue(j);
					// Debug.println("[evaluate] Value = " +
					// slotValue.getClass().getName());
					values.addValue(this.locus.executor.evaluate(slotValue));
				}
				structuredValue
						.setFeatureValue(slot.definingFeature, values, 0);
			}

			value = structuredValue;
		}

		return value;
	} // evaluate

} // InstanceValueEvaluation
