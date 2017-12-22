
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

package fuml.semantics.loci;

import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.syntax.classification.ClassifierList;
import fuml.syntax.commonbehavior.Behavior;

public class Locus extends org.modeldriven.fuml.FumlObject {

	public String identifier = Integer.toHexString(this.hashCode());
	public fuml.semantics.loci.Executor executor = null;
	public fuml.semantics.loci.ExecutionFactory factory = null;
	public fuml.semantics.structuredclassifiers.ExtensionalValueList extensionalValues = new fuml.semantics.structuredclassifiers.ExtensionalValueList();

	public void setExecutor(fuml.semantics.loci.Executor executor) {
		// Set the executor for this locus.

		this.executor = executor;
		this.executor.locus = this;
	} // setExecutor

	public void setFactory(fuml.semantics.loci.ExecutionFactory factory) {
		// Set the factory for this locus.

		this.factory = factory;
		this.factory.locus = this;
	} // setFactory

	public fuml.semantics.structuredclassifiers.ExtensionalValueList getExtent(
			fuml.syntax.classification.Classifier classifier) {
		// Return the set of extensional values at this locus which have the
		// given classifier as a type.

		ExtensionalValueList extent = new ExtensionalValueList();

		ExtensionalValueList extensionalValues = this.extensionalValues;
		for (int i = 0; i < extensionalValues.size(); i++) {
			ExtensionalValue value = extensionalValues.getValue(i);
			ClassifierList types = value.getTypes();

			boolean conforms = false;
			int j = 1;
			while (!conforms & j <= types.size()) {
				conforms = this.conforms(types.getValue(j - 1), classifier);
				j = j + 1;
			}

			if (conforms) {
				extent.addValue(value);
			}
		}

		return extent;
	} // getExtent

	public void add(fuml.semantics.structuredclassifiers.ExtensionalValue value) {
		// Add the given extensional value to this locus

		value.locus = this;
		value.identifier = this.identifier + "#" + this.makeIdentifier(value);
		this.extensionalValues.addValue(value);
		
	} // add
	
	public String makeIdentifier(fuml.semantics.structuredclassifiers.ExtensionalValue value) {
		// Return an identifier for the given (newly created) extensional value.
		
		// [No normative specification. A conforming implementation may create an identifier 
		// an identifier in any way such that all identifiers for extensional values created
		// at any one locus are unique.]
		
		// Non-normative Java implementation
		return Integer.toHexString(value.hashCode());
	} // makeIdentifier

	public void remove(fuml.semantics.structuredclassifiers.ExtensionalValue value) {
		// Remove the given extensional value from this locus.

		value.locus = null;

		boolean notFound = true;
		int i = 1;
		while (notFound & i <= this.extensionalValues.size()) {
			if (this.extensionalValues.getValue(i - 1) == value) {
				this.extensionalValues.remove(i - 1);
				notFound = false;
			}
			i = i + 1;
		}
	} // remove

	public fuml.semantics.structuredclassifiers.Object_ instantiate(
			fuml.syntax.structuredclassifiers.Class_ type) {
		// Instantiate the given class at this locus.

		Object_ object = null;

		if (type instanceof Behavior) {
			object = this.factory.createExecution((Behavior) type, null);
		} else {
			object = new Object_();

			object.types.addValue(type);
			object.createFeatureValues();
			this.add(object);
		}

		return object;
	} // instantiate

	public boolean conforms(fuml.syntax.classification.Classifier type,
			fuml.syntax.classification.Classifier classifier) {
		// Test if a type conforms to a given classifier, that is, the type is
		// equal to or a descendant of the classifier.

		boolean doesConform = false;

		if (type == classifier) {
			doesConform = true;
		} else {
			int i = 1;
			while (!doesConform & i <= type.general.size()) {
				doesConform = this.conforms(type.general.getValue(i - 1),
						classifier);
				i = i + 1;
			}
		}

		return doesConform;

	} // conforms

} // Locus
