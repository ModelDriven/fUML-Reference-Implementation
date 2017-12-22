
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.structuredclassifiers;

public class Reference extends fuml.semantics.simpleclassifiers.StructuredValue {

	public fuml.semantics.structuredclassifiers.Object_ referent = null;

	public void startBehavior(
			fuml.syntax.structuredclassifiers.Class_ classifier,
			fuml.semantics.commonbehavior.ParameterValueList inputs) {
		// Asynchronously start the behavior of the given classifier for the
		// referent object.

		this.referent.startBehavior(classifier, inputs);
	} // startBehavior

	public fuml.semantics.commonbehavior.Execution dispatch(
			fuml.syntax.classification.Operation operation) {
		// Dispatch the given operation to the referent object.

		return this.referent.dispatch(operation);
	} // dispatch

	public void send(
			fuml.semantics.commonbehavior.EventOccurrence eventOccurrence) {
		// Send the given event occurrence to the referent object.

		this.referent.send(eventOccurrence);
	} // send

	public void destroy() {
		// Destroy the referent.

		this.referent.destroy();
	} // destroy

	public boolean equals(fuml.semantics.classification.Value otherValue) {
		// Test if this reference is equal to the otherValue.
		// To be equal, the otherValue must also be a reference, with the same
		// referent as this reference.

		boolean isEqual = false;
		if (otherValue instanceof Reference) {
			if (this.referent == null) {
				isEqual = ((Reference)otherValue).referent == null;
			} else {
				isEqual = this.referent.equals(((Reference) otherValue).referent);
			}
		}

		return isEqual;

	} // equals

	public fuml.semantics.classification.Value copy() {
		// Create a new reference with the same referent as this reference.

		Reference newValue = (Reference) (super.copy());

		newValue.referent = this.referent;

		return newValue;
	} // copy

	protected fuml.semantics.classification.Value new_() {
		// Create a new reference with no referent.

		return new Reference();
	} // new_

	public fuml.syntax.classification.ClassifierList getTypes() {
		// Get the types of the referent object.

		return this.referent.getTypes();
	} // getTypes

	public fuml.semantics.simpleclassifiers.FeatureValue getFeatureValue(
			fuml.syntax.classification.StructuralFeature feature) {
		// Get the feature value associated with the given feature in the
		// referent object.

		return this.referent.getFeatureValue(feature);
	} // getFeatureValue

	public void setFeatureValue(
			fuml.syntax.classification.StructuralFeature feature,
			fuml.semantics.classification.ValueList values, int position) {
		// Set the values associated with the given feature in the referent
		// object.

		this.referent.setFeatureValue(feature, values, position);
	} // setFeatureValue

	public fuml.semantics.simpleclassifiers.FeatureValueList getFeatureValues() {
		// Return the feature values of the referent.

		return this.referent.getFeatureValues();
	} // getFeatureValues

	public String toString() {
		return "Reference to " + this.referent.toString();
	} // toString

} // Reference
