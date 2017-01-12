
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

package org.modeldriven.fuml.test.builtin.library;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class IntegerFunctions extends org.modeldriven.fuml.test.builtin.library.PrimitiveFunctions {

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
						new org.modeldriven.fuml.library.integerfunctions.IntegerPlusFunctionBehaviorExecution(),
						factory);
		this.integerMinus = this
				.createBinaryOperator(
						"IntegerMinus",
						integerType,
						new org.modeldriven.fuml.library.integerfunctions.IntegerMinusFunctionBehaviorExecution(),
						factory);
		this.integerTimes = this
				.createBinaryOperator(
						"IntegerTimes",
						integerType,
						new org.modeldriven.fuml.library.integerfunctions.IntegerTimesFunctionBehaviorExecution(),
						factory);
		this.integerDivide = this
				.createBinaryOperator(
						"IntegerDivide",
						integerType,
						new org.modeldriven.fuml.library.integerfunctions.IntegerDivideFunctionBehaviorExecution(),
						factory);
		this.integerNegate = this
				.createUnaryOperator(
						"IntegerNegate",
						integerType,
						new org.modeldriven.fuml.library.integerfunctions.IntegerNegateFunctionBehaviorExecution(),
						factory);
		this.integerGreater = this
				.createComparisonOperator(
						"IntegerGreater",
						integerType,
						booleanType,
						new org.modeldriven.fuml.library.integerfunctions.IntegerGreaterThanFunctionBehaviorExecution(),
						factory);
	} // IntegerFunctions

} // IntegerFunctions
