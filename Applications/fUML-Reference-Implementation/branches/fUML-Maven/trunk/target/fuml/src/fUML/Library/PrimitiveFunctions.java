
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

public abstract class PrimitiveFunctions extends
		fUML.Library.PrimitiveBehaviors {

	protected fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior createBinaryOperator(
			String name,
			fUML.Syntax.Classes.Kernel.Classifier type,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Create a binary operator for the given type.

		ParameterList parameters = new ParameterList();
		parameters.addValue(this.createInputParameter("first", type, 1, 1));
		parameters.addValue(this.createInputParameter("second", type, 1, 1));
		parameters.addValue(this.createReturnParameter(type, 1, 1));

		return this.createPrimitiveFunction(name, parameters, implementation,
				factory);
	} // createBinaryOperator

	protected fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior createUnaryOperator(
			String name,
			fUML.Syntax.Classes.Kernel.Classifier type,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Create a unary operator for the given type.

		ParameterList parameters = new ParameterList();
		parameters.addValue(this.createInputParameter("argument", type, 1, 1));
		parameters.addValue(this.createReturnParameter(type, 1, 1));

		return this.createPrimitiveFunction(name, parameters, implementation,
				factory);
	} // createUnaryOperator

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior createComparisonOperator(
			String name,
			fUML.Syntax.Classes.Kernel.Classifier type,
			fUML.Syntax.Classes.Kernel.PrimitiveType booleanType,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Create a comparison operator for the given type.

		ParameterList parameters = new ParameterList();
		parameters.addValue(this.createInputParameter("first", type, 1, 1));
		parameters.addValue(this.createInputParameter("second", type, 1, 1));
		parameters.addValue(this.createReturnParameter(booleanType, 1, 1));

		return this.createPrimitiveFunction(name, parameters, implementation,
				factory);
	} // createComparisonOperator

	protected fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior createPrimitiveFunction(
			String name,
			fUML.Syntax.Classes.Kernel.ParameterList parameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution implementation,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		// Create a primitive function behavior and add its implementation to
		// the given locus.

		return (FunctionBehavior) (this.addPrimitiveBehavior(name, parameters,
				new FunctionBehavior(), implementation, factory));
	} // createPrimitiveFunction

} // PrimitiveFunctions
