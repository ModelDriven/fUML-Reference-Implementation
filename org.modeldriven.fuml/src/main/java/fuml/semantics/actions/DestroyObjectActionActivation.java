
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

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Link;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.DestroyObjectAction;
import fuml.syntax.classification.AggregationKind;
import fuml.syntax.classification.Property;

public class DestroyObjectActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Get the value on the target input pin.
		// If the value is not a reference, then the action has no effect.
		// Otherwise, do the following.
		// If isDestroyLinks is true, destroy all links in which the referent
		// participates.
		// If isDestroyOwnedObjects is true, destroy all objects owned by the
		// referent via composition links.
		// Destroy the referent object.

		DestroyObjectAction action = (DestroyObjectAction) (this.node);
		Value value = this.takeTokens(action.target).getValue(0);

		this.destroyObject(value, action.isDestroyLinks,
				action.isDestroyOwnedObjects);

	} // doAction

	public void destroyObject(fuml.semantics.values.Value value,
			boolean isDestroyLinks, boolean isDestroyOwnedObjects) {
		// If the given value is a reference, then destroy the referenced
		// object, per the given destroy action attribute values.

		// Debug.println("[destroyObject] object = " + value.objectId());

		if (value instanceof Reference) {
			Reference reference = (Reference) value;

			if (isDestroyLinks | isDestroyOwnedObjects) {
				Debug.println("[destroyObject] Destroying links...");
				ExtensionalValueList extensionalValues = this
						.getExecutionLocus().extensionalValues;
				for (int i = 0; i < extensionalValues.size(); i++) {
					ExtensionalValue extensionalValue = extensionalValues
							.getValue(i);
					if (extensionalValue instanceof Link) {
						Link link = (Link) extensionalValue;
						if (this.valueParticipatesInLink(reference, link)) {
							if (isDestroyLinks
									| this.objectIsComposite(reference, link)) {
								// Debug.println("[destroyObject] Destroying link "
								// + link.objectId());
								link.destroy();
							}
						}
					}
				}
			}

			if (isDestroyOwnedObjects) {
				Debug.println("[destroyObject] Destroying owned objects...");
				FeatureValueList objectFeatureValues = reference
						.getFeatureValues();
				for (int i = 0; i < objectFeatureValues.size(); i++) {
					FeatureValue featureValue = objectFeatureValues.getValue(i);
					if (((Property) featureValue.feature).aggregation == AggregationKind.composite) {
						ValueList values = featureValue.values;
						for (int j = 0; j < values.size(); j++) {
							Value ownedValue = values.getValue(j);
							this.destroyObject(ownedValue, isDestroyLinks,
									isDestroyOwnedObjects);
						}
					}
				}
			}

			reference.destroy();
		}
	} // destroyObject

	public boolean objectIsComposite(
			fuml.semantics.structuredclassifiers.Reference reference,
			fuml.semantics.structuredclassifiers.Link link) {
		// Test whether the given reference participates in the given link as a
		// composite.

		FeatureValueList linkFeatureValues = link.getFeatureValues();

		boolean isComposite = false;
		int i = 1;
		while (!isComposite & i <= linkFeatureValues.size()) {
			FeatureValue featureValue = linkFeatureValues.getValue(i - 1);
			if (!featureValue.values.getValue(0).equals(reference)
					& ((Property) featureValue.feature).aggregation == AggregationKind.composite) {
				isComposite = true;
			}
			i = i + 1;
		}

		return isComposite;

	} // objectIsComposite

} // DestroyObjectActionActivation
