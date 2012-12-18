
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

public class Executor extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Loci.LociL1.Locus locus = null;

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList execute(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior behavior,
			fUML.Semantics.Classes.Kernel.Object_ context,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputs) {
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

	public fUML.Semantics.Classes.Kernel.Value evaluate(
			fUML.Syntax.Classes.Kernel.ValueSpecification specification) {
		// Evaluate the given value specification, returning the specified
		// value.

		// Debug.println("[evaluate] Start...");
		return this.locus.factory.createEvaluation(specification).evaluate();
	} // evaluate

	public fUML.Semantics.Classes.Kernel.Reference start(
			fUML.Syntax.Classes.Kernel.Class_ type,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputs) {
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
