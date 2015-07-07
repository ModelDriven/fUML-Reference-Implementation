
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
	
	public fUML.Semantics.Classes.Kernel.FeatureValueList getMemberValues() {
		// Return the feature values for this structured value that are for structural
		// features that are members of one of the types of the structured value.
		// (That is, they are owned are inherited, excluding private features of
		// supertypes that are not inherited.)
		
		FeatureValueList featureValues = this.getFeatureValues();
		FeatureValueList memberValues = new FeatureValueList();
		
		ClassifierList types = this.getTypes();
		for (int i = 0; i < featureValues.size(); i++) {
			FeatureValue featureValue = featureValues.getValue(i);
			Boolean isMember = false;
			int j = 1;
			while (j <= types.size() & !isMember) {
				Classifier type = types.getValue(j-1);
				NamedElementList members = type.member;
				int k = 1;
				while (k <= members.size() & !isMember) {
					NamedElement member = members.getValue(k-1);
					isMember = featureValue.feature == member;
					k = k + 1;
				}
				j = j + 1;
			}
			if (isMember) {
				memberValues.addValue(featureValue);
			}
		}
		
		return memberValues;
	};

	public void createFeatureValues() {
		// Create empty feature values for all structural features of the types 
		// of this structured value and all its supertypes (including private
		// features that are not inherited).

		ClassifierList types = this.getTypes();

		for (int i = 0; i < types.size(); i++) {
			Classifier type = types.getValue(i);
			this.createFeatureValuesForType(type);
		}
	}
	
	public void createFeatureValuesForType(Classifier type) {
		// Create empty feature values for all structural features of the given 
		// type and all its supertypes (including private features that are not 
		// inherited).

		// Create feature values for the owned structural features of the given
		// type. (Any common structural values that have already been created
		// previously will simply have there values set to empty again.)
		NamedElementList ownedMembers = type.ownedMember;
		for (int j = 0; j < ownedMembers.size(); j++) {
			NamedElement ownedMember = ownedMembers.getValue(j);
			if (ownedMember instanceof StructuralFeature) {
				this.setFeatureValue((StructuralFeature) ownedMember, new ValueList(), 0);
			}
		}
		
		// Create feature values for the structural features of the supertypes
		// of the given type. (Note that the feature values for supertype
		// features always come after the feature values for owned features.)
		ClassifierList supertypes = type.general;		
		for (int i = 0; i < supertypes.size(); i++) {
			Classifier supertype = supertypes.getValue(i);
			this.createFeatureValuesForType(supertype);
		}
	}

} // StructuredValue
