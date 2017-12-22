
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

package fuml.semantics.loci;

import fuml.Debug;
import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Object_;
import fuml.semantics.structuredclassifiers.Reference;

public class Executor extends org.modeldriven.fuml.FumlObject {

	public fuml.semantics.loci.Locus locus = null;

	public fuml.semantics.commonbehavior.ParameterValueList execute(
			fuml.syntax.commonbehavior.Behavior behavior,
			fuml.semantics.structuredclassifiers.Object_ context,
			fuml.semantics.commonbehavior.ParameterValueList inputs) {
		// Execute the given behavior with the given input values in the given
		// context, producing the given output values.
		// There must be one input parameter value for each input (in or in-out)
		// parameter of the behavior.
		// The returned values include one parameter value for each output
		// (in-out, out or return) parameter of the behavior.
		// The execution instance is destroyed at completion.

		Execution execution = this.locus.factory.createExecution(behavior,
				context);

		for (int i = 0; i < inputs.size(); i++) {
			execution.setParameterValue(inputs.getValue(i));
		}

		execution.execute();
		ParameterValueList outputValues = execution.getOutputParameterValues();
		execution.destroy();

		return outputValues;
	} // execute

	public fuml.semantics.classification.Value evaluate(
			fuml.syntax.values.ValueSpecification specification) {
		// Evaluate the given value specification, returning the specified
		// value.

		// Debug.println("[evaluate] Start...");
		return this.locus.factory.createEvaluation(specification).evaluate();
	} // evaluate

	public fuml.semantics.structuredclassifiers.Reference start(
			fuml.syntax.structuredclassifiers.Class_ type,
			fuml.semantics.commonbehavior.ParameterValueList inputs) {
		// Instantiate the given class and start any behavior of the resulting
		// object.
		// (The behavior of an object includes any classifier behaviors for an
		// active object or the class of the object itself, if that is a
		// behavior.)

		Debug.println("[start] Starting " + type.name + "...");

		Object_ object = this.locus.instantiate(type);

		Debug.println("[start] Object = " + object);
		object.startBehavior(type, inputs);

		Reference reference = new Reference();
		reference.referent = object;

		return reference;
	} // start

} // Executor
