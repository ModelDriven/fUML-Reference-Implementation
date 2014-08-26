
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

package fUML.Library;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

public abstract class PrimitiveBehaviors extends
		org.modeldriven.fuml.FumlObject {

	protected fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior createPrimitiveBehavior(
			String name,
			fUML.Syntax.Classes.Kernel.ParameterList parameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Create a primitive (opaque) behavior and add its implementation to
		// the given factory.

		return this.addPrimitiveBehavior(name, parameters,
				new OpaqueBehavior(), implementation, factory);

	} // createPrimitiveBehavior

	protected fUML.Syntax.Classes.Kernel.Parameter createInputParameter(
			String name, fUML.Syntax.Classes.Kernel.Classifier type, int lower,
			int upper) {
		// Create an input parameter with the given name, type and multiplicity
		// bounds.

		Parameter parameter = new Parameter();
		parameter.name = name;
		parameter.type = type;
		parameter.direction = ParameterDirectionKind.in;
		parameter.multiplicityElement = new MultiplicityElement();
		this.setMultiplicity(parameter.multiplicityElement, lower, upper);

		return parameter;
	} // createInputParameter

	protected fUML.Syntax.Classes.Kernel.Parameter createReturnParameter(
			fUML.Syntax.Classes.Kernel.Classifier type, int lower, int upper) {
		// Create a return parameter with the given type and multiplicity
		// bounds.

		Parameter parameter = new Parameter();
		parameter.name = "result";
		parameter.type = type;
		parameter.direction = ParameterDirectionKind.return_;
		parameter.multiplicityElement = new MultiplicityElement();
		this.setMultiplicity(parameter.multiplicityElement, lower, upper);

		return parameter;
	} // createReturnParameter

	protected void setMultiplicity(
			fUML.Syntax.Classes.Kernel.MultiplicityElement element, int lower,
			int upper) {
		UnlimitedNatural unlimitedNatural = new UnlimitedNatural();
		unlimitedNatural.naturalValue = upper;

		element.lower = lower;
		element.upper = unlimitedNatural;
	} // setMultiplicity

	protected fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior addPrimitiveBehavior(
			String name,
			fUML.Syntax.Classes.Kernel.ParameterList parameters,
			fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior type,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Add a primitive function to the given locus.

		// Debug.println("[addPrimitiveFunction] name = " + name);

		type.name = name;
		type.ownedParameter = parameters;

		// Debug.println("[addPrimitiveFunction] Adding implementation...");

		implementation.types.addValue(type);
		factory.addPrimitiveBehaviorPrototype(implementation);

		return type;

	} // addPrimitiveBehavior

} // PrimitiveBehaviors
