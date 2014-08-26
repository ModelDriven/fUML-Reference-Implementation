
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

public class IntegerFunctions extends fUML.Library.PrimitiveFunctions {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerPlus = null;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerMinus = null;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerTimes = null;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerDivide = null;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerNegate = null;
	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior integerGreater = null;

	public IntegerFunctions(
			fUML.Syntax.Classes.Kernel.PrimitiveType integerType,
			fUML.Syntax.Classes.Kernel.PrimitiveType booleanType,
			fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		this.integerPlus = this
				.createBinaryOperator(
						"IntegerPlus",
						integerType,
						new fUML.Library.IntegerFunctionImplementation.IntegerPlusFunctionBehaviorExecution(),
						factory);
		this.integerMinus = this
				.createBinaryOperator(
						"IntegerMinus",
						integerType,
						new fUML.Library.IntegerFunctionImplementation.IntegerMinusFunctionBehaviorExecution(),
						factory);
		this.integerTimes = this
				.createBinaryOperator(
						"IntegerTimes",
						integerType,
						new fUML.Library.IntegerFunctionImplementation.IntegerTimesFunctionBehaviorExecution(),
						factory);
		this.integerDivide = this
				.createBinaryOperator(
						"IntegerDivide",
						integerType,
						new fUML.Library.IntegerFunctionImplementation.IntegerDivideFunctionBehaviorExecution(),
						factory);
		this.integerNegate = this
				.createUnaryOperator(
						"IntegerNegate",
						integerType,
						new fUML.Library.IntegerFunctionImplementation.IntegerNegateFunctionBehaviorExecution(),
						factory);
		this.integerGreater = this
				.createComparisonOperator(
						"IntegerGreater",
						integerType,
						booleanType,
						new fUML.Library.IntegerFunctionImplementation.IntegerGreaterFunctionBehaviorExecution(),
						factory);
	} // IntegerFunctions

} // IntegerFunctions
