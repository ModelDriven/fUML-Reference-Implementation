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

import org.modeldriven.fuml.library.Library;

import fuml.semantics.commonbehavior.OpaqueBehaviorExecution;
import fuml.semantics.loci.ExecutionFactory;
import fuml.syntax.commonbehavior.FunctionBehavior;
import fuml.syntax.commonbehavior.OpaqueBehavior;

public class IntegerFunctions {

	public static final FunctionBehavior integerPlus = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-plus");
	public static final FunctionBehavior integerMinus = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-minus");
	public static final FunctionBehavior integerTimes = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-times");
	public static final FunctionBehavior integerDivide = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-Div");
	public static final FunctionBehavior integerNegate = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-Neg");
	public static final FunctionBehavior integerGreater = (FunctionBehavior)Library.getInstance().lookup("PrimitiveBehaviors-IntegerFunctions-gt");

	public static void addFunctions(ExecutionFactory factory) {
		addPrimitiveBehavior(integerPlus, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerPlusFunctionBehaviorExecution(),
				factory);
		addPrimitiveBehavior(integerMinus, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerMinusFunctionBehaviorExecution(),
				factory);
		addPrimitiveBehavior(integerTimes, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerTimesFunctionBehaviorExecution(),
				factory);
		addPrimitiveBehavior(integerDivide, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerDivideFunctionBehaviorExecution(),
				factory);
		addPrimitiveBehavior(integerNegate, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerNegateFunctionBehaviorExecution(),
				factory);
		addPrimitiveBehavior(integerGreater, 
				new org.modeldriven.fuml.library.integerfunctions.IntegerGreaterThanFunctionBehaviorExecution(),
				factory);
	}
	
	public static void addPrimitiveBehavior(
			OpaqueBehavior type,
			OpaqueBehaviorExecution implementation,
			ExecutionFactory factory) {
		implementation.types.addValue(type);
		factory.addPrimitiveBehaviorPrototype(implementation);

	}

} // IntegerFunctions
