
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

package fUML.Semantics.Loci.LociL1;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public class Locus extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Loci.LociL1.Executor executor = null;
	public fUML.Semantics.Loci.LociL1.ExecutionFactory factory = null;
	public fUML.Semantics.Classes.Kernel.ExtensionalValueList extensionalValues = new fUML.Semantics.Classes.Kernel.ExtensionalValueList();

	public void setExecutor(fUML.Semantics.Loci.LociL1.Executor executor) {
		// Set the executor for this locus.

		this.executor = executor;
		this.executor.locus = this;
	} // setExecutor

	public void setFactory(fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Set the factory for this locus.

		this.factory = factory;
		this.factory.locus = this;
	} // setFactory

	public fUML.Semantics.Classes.Kernel.ExtensionalValueList getExtent(
			fUML.Syntax.Classes.Kernel.Classifier classifier) {
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

	public void add(fUML.Semantics.Classes.Kernel.ExtensionalValue value) {
		// Add the given extensional value to this locus

		value.locus = this;
		this.extensionalValues.addValue(value);
	} // add

	public void remove(fUML.Semantics.Classes.Kernel.ExtensionalValue value) {
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

	public fUML.Semantics.Classes.Kernel.Object_ instantiate(
			fUML.Syntax.Classes.Kernel.Class_ type) {
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

	public boolean conforms(fUML.Syntax.Classes.Kernel.Classifier type,
			fUML.Syntax.Classes.Kernel.Classifier classifier) {
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
