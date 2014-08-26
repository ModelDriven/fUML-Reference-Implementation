
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

public class Reference extends fUML.Semantics.Classes.Kernel.StructuredValue {

	public fUML.Semantics.Classes.Kernel.Object_ referent = null;

	public void startBehavior(
			fUML.Syntax.Classes.Kernel.Class_ classifier,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputs) {
		// Asynchronously start the behavior of the given classifier for the
		// referent object.

		this.referent.startBehavior(classifier, inputs);
	} // startBehavior

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
			fUML.Syntax.Classes.Kernel.Operation operation) {
		// Dispatch the given operation to the referent object.

		return this.referent.dispatch(operation);
	} // dispatch

	public void send(
			fUML.Semantics.CommonBehaviors.Communications.SignalInstance signalInstance) {
		// Send the given signal instance to the referent object.

		this.referent.send(signalInstance);
	} // send

	public void destroy() {
		// Destroy the referent.

		this.referent.destroy();
	} // destroy

	public boolean equals(fUML.Semantics.Classes.Kernel.Value otherValue) {
		// Test if this reference is equal to the otherValue.
		// To be equal, the otherValue must also be a reference, with the same
		// referent as this reference.

		boolean isEqual = false;
		if (otherValue instanceof Reference) {
			isEqual = (((Reference) otherValue).referent == this.referent);
		}

		return isEqual;

	} // equals

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new reference with the same referent as this reference.

		Reference newValue = (Reference) (super.copy());

		newValue.referent = this.referent;

		return newValue;
	} // copy

	protected fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new reference with no referent.

		return new Reference();
	} // new_

	public fUML.Syntax.Classes.Kernel.ClassifierList getTypes() {
		// Get the types of the referent object.

		return this.referent.getTypes();
	} // getTypes

	public fUML.Semantics.Classes.Kernel.FeatureValue getFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature) {
		// Get the feature value associated with the given feature in the
		// referent object.

		return this.referent.getFeatureValue(feature);
	} // getFeatureValue

	public void setFeatureValue(
			fUML.Syntax.Classes.Kernel.StructuralFeature feature,
			fUML.Semantics.Classes.Kernel.ValueList values, int position) {
		// Set the values associated with the given feature in the referent
		// object.

		this.referent.setFeatureValue(feature, values, position);
	} // setFeatureValue

	public fUML.Semantics.Classes.Kernel.FeatureValueList getFeatureValues() {
		// Return the feature values of the referent.

		return this.referent.getFeatureValues();
	} // getFeatureValues

	public String toString() {
		return "Reference to " + this.referent.toString();
	} // toString

} // Reference
