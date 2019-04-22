
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
		// referent via either composite attributes or composition links.
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

		if (value instanceof Reference) {
			Reference reference = (Reference) value;
			Debug.println("[destroyObject] object = " + reference.referent.identifier);

			if (isDestroyLinks | isDestroyOwnedObjects) {
				ExtensionalValueList extensionalValues = this
						.getExecutionLocus().extensionalValues;
				for (int i = 0; i < extensionalValues.size(); i++) {
					ExtensionalValue extensionalValue = extensionalValues
							.getValue(i);
					if (extensionalValue instanceof Link) {
						Link link = (Link) extensionalValue;
						if (this.valueParticipatesInLink(reference, link)) {
							if (isDestroyOwnedObjects) {
								Value compositeValue = 
										this.getCompositeValue(reference, link);
								if (compositeValue != null) {
									Debug.println("[destroyObject] Destroying (linked) owned object ...");
									this.destroyObject(compositeValue, isDestroyLinks, 
											isDestroyOwnedObjects);
								}
							}
							if (isDestroyLinks & !link.getTypes().isEmpty()) {
								link.destroy();
							}
						}
					}
				}
			}

			if (isDestroyOwnedObjects) {
				FeatureValueList objectFeatureValues = reference
						.getFeatureValues();
				for (int i = 0; i < objectFeatureValues.size(); i++) {
					FeatureValue featureValue = objectFeatureValues.getValue(i);
					if (((Property) featureValue.feature).aggregation == AggregationKind.composite) {
						Debug.println("[destroyObject] Destroying owned objects...");
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

	public Value getCompositeValue(
			fuml.semantics.structuredclassifiers.Reference reference,
			fuml.semantics.structuredclassifiers.Link link) {
		// If the given reference participates in the given link as a composite,
		// then return the opposite value. Otherwise return null.

		FeatureValueList linkFeatureValues = link.getFeatureValues();

		Value compositeValue = null;
		int i = 1;
		while (compositeValue == null & i <= linkFeatureValues.size()) {
			FeatureValue featureValue = linkFeatureValues.getValue(i - 1);
			Value value = featureValue.values.getValue(0);
			if (!value.equals(reference) &
				((Property) featureValue.feature).aggregation == AggregationKind.composite) {
				compositeValue = value;
			}
			i = i + 1;
		}

		return compositeValue;

	} // objectIsComposite

} // DestroyObjectActionActivation
