
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

public abstract class StructuredValue extends
		fUML.Semantics.Classes.Kernel.Value {

	public fUML.Syntax.Classes.Kernel.ValueSpecification specify() {
		// Return an instance value that specifies this structured value.

		// Debug.println("[specify] StructuredValue...");

		InstanceValue instanceValue = new InstanceValue();
		InstanceSpecification instance = new InstanceSpecification();

		instanceValue.type = null;
		instanceValue.instance = instance;

		instance.classifier = this.getTypes();

		FeatureValueList featureValues = this.getFeatureValues();
		// Debug.println("[specify] " + featureValues.size() + " feature(s).");

		for (int i = 0; i < featureValues.size(); i++) {
			FeatureValue featureValue = featureValues.getValue(i);

			Slot slot = new Slot();
			slot.definingFeature = featureValue.feature;

			// Debug.println("[specify] feature = " + featureValue.feature.name
			// + ", " + featureValue.values.size() + " value(s).");

			ValueList values = featureValue.values;
			for (int j = 0; j < values.size(); j++) {
				Value value = values.getValue(j);
				// Debug.println("[specify] value = " + value);
				slot.value.addValue(value.specify());
			}

			instance.slot.addValue(slot);
		}

		return instanceValue;
	} // specify

	public abstract fUML.Semantics.Classes.Kernel.FeatureValue getFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature);

	public abstract void setFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature,
			fUML.Semantics.Classes.Kernel.ValueList values, int position);

	public abstract fUML.Semantics.Classes.Kernel.FeatureValueList getFeatureValues();

	public void createFeatureValues() {
		// Create empty feature values for all structural features, direct and
		// inherited, of the types of this structured value.

		ClassifierList types = this.getTypes();

		for (int i = 0; i < types.size(); i++) {
			Classifier type = types.getValue(i);
			NamedElementList members = type.member;

			for (int j = 0; j < members.size(); j++) {
				NamedElement member = members.getValue(j);
				if (member instanceof StructuralFeature) {
					this.setFeatureValue((StructuralFeature) member,
							new ValueList(), 0);
				}
			}
		}
	} // createFeatureValues

} // StructuredValue
