
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

package fuml.semantics.simpleclassifiers;

import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.ClassifierList;
import fuml.syntax.classification.InstanceSpecification;
import fuml.syntax.classification.InstanceValue;
import fuml.syntax.classification.Property;
import fuml.syntax.classification.Slot;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.classification.StructuralFeatureList;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.commonstructure.NamedElementList;

public abstract class StructuredValue extends
		fuml.semantics.values.Value {

	public fuml.syntax.values.ValueSpecification specify() {
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

	public abstract fuml.semantics.simpleclassifiers.FeatureValue getFeatureValue(
			fuml.syntax.classification.StructuralFeature feature);

	public abstract void setFeatureValue(
			fuml.syntax.classification.StructuralFeature feature,
			fuml.semantics.values.ValueList values, int position);

	public abstract fuml.semantics.simpleclassifiers.FeatureValueList getFeatureValues();
	
	public StructuralFeatureList getMemberFeatures(Classifier type) {
		// Return the features for this structured value that are members of the 
		// given type. (That is, they are owned or inherited by the given type, 
		// excluding private features of supertypes that are not inherited.)
		
		StructuralFeatureList features = this.getStructuralFeatures();
		StructuralFeatureList memberFeatures = new StructuralFeatureList();
		
		if (type != null) {
			NamedElementList members = type.member;
			for (int i = 0; i < features.size(); i++) {
				StructuralFeature feature = features.getValue(i);
				Boolean isMember = false;
				int k = 1;
				while (k <= members.size() & !isMember) {
					NamedElement member = members.getValue(k-1);
					isMember = feature == member;
					k = k + 1;
				}
				if (isMember) {
					memberFeatures.addValue(feature);
				}
			}
		}
		
		return memberFeatures;
	};
	
	public StructuralFeatureList getStructuralFeatures() {
		// Get all structural features of the types of this structured 
		// value and all of their supertypes (including private features 
		// that are not inherited).
		
		StructuralFeatureList features = new StructuralFeatureList();
		ClassifierList types = this.getTypes();

		for (int i = 0; i < types.size(); i++) {
			Classifier type = types.getValue(i);
			StructuralFeatureList typeFeatures = this.getStructuralFeaturesForType(type);
			for (int j = 0; j < typeFeatures.size(); j++) {
				NamedElement supertypeFeature = typeFeatures.getValue(j);
				features.addValue((StructuralFeature)supertypeFeature);
			}
		}
		
		return features;
	}
	
	public StructuralFeatureList getStructuralFeaturesForType(Classifier type) {
		// Get all structural features of the given type and all of its 
		// supertypes (including private features that are not inherited).
		
		StructuralFeatureList features = new StructuralFeatureList();
		
		// Get feature values for the owned structural features of the given type.
		NamedElementList ownedMembers = type.ownedMember;
		for (int j = 0; j < ownedMembers.size(); j++) {
			NamedElement ownedMember = ownedMembers.getValue(j);
			if (ownedMember instanceof StructuralFeature) {
				features.addValue((StructuralFeature)ownedMember);
			}
		}
		
		// Add features for the structural features of the supertypes
		// of the given type. (Note that the features for supertypes
		// always come after the owned features.)
		ClassifierList supertypes = type.general;		
		for (int i = 0; i < supertypes.size(); i++) {
			Classifier supertype = supertypes.getValue(i);
			StructuralFeatureList supertypeFeatures = this.getStructuralFeaturesForType(supertype);
			for (int j = 0; j < supertypeFeatures.size(); j++) {
				NamedElement supertypeFeature = supertypeFeatures.getValue(j);
				features.addValue((StructuralFeature)supertypeFeature);
			}
		}
		
		return features;
	}

	public void createFeatureValues() {
		// Create empty feature values for all non-association-end structural 
		// features of the types of this structured value and all its supertypes 
		// (including private features that are not inherited).

		this.addFeatureValues(new FeatureValueList());
	}
	
	public void addFeatureValues(FeatureValueList oldFeatureValues) {
		// Add feature values for all non-association-end structural features 
		// of the types of this structured value and all its supertypes 
		// (including private features that are not inherited). If a feature 
		// has an old feature value in the given list, then use that to 
		// initialize the values of the corresponding new feature value. 
		// Otherwise leave the values of the new feature value empty.

		// Note: Any common features that appear twice in the list will simply
		// have their values set multiple times to the same thing.
		StructuralFeatureList features = this.getStructuralFeatures();
		for (int i = 0; i < features.size(); i++) {
			StructuralFeature feature = features.getValue(i);
			if (!this.checkForAssociationEnd(feature)) {
				this.setFeatureValue(feature, 
						this.getValues(feature, oldFeatureValues), 0);
			}
		}
	}
	
	public boolean checkForAssociationEnd(StructuralFeature feature) {
		boolean isAssociationEnd = false;
		if (feature instanceof Property) {
			isAssociationEnd = ((Property)feature).association != null;
		}
		return isAssociationEnd;
	}
	
	public ValueList getValues(NamedElement feature, FeatureValueList featureValues) {
		// Return the values from the feature value in the given list for the 
		// given feature. If there is no such feature value, return an empty
		// list.
		
		FeatureValue foundFeatureValue = null;
		
		int i = 1;
		while (foundFeatureValue == null & i <= featureValues.size()) {
			FeatureValue featureValue = featureValues.getValue(i-1);
			if (featureValue.feature == feature) {
				foundFeatureValue = featureValue;
			}
			i = i + 1;
		}
		
		ValueList values;
		if (foundFeatureValue == null) {
			values = new ValueList();
		} else {
			values = foundFeatureValue.values;
		}
		
		return values;
	}

} // StructuredValue
