/*
 * Copyright (c) 2008 Lockheed Martin Corporation.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.integerfunctions;

import org.modeldriven.fuml.library.LibraryTestSetup;
import org.modeldriven.fuml.library.booleanfunctions.BooleanConversion;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class IntegerDivideFunctionBehaviorExecutionTest extends TestCase {
	
	ParameterValueList inputParameters;
	ParameterValueList outputParameters;

	/**
	 * Set up the ParameterValueLists to simulate the fUML system before calling
	 * the doBody() method on the library classes.
	 */
	@Override
	public void setUp() {
		LibraryTestSetup libraryTestSetup = new LibraryTestSetup();
		inputParameters = libraryTestSetup.setupInputParameterList();
		outputParameters = libraryTestSetup.setupOutputParameterList();
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivideFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 4, inputParameters);
		IntegerDivFunctionBehaviorExecution obj = new IntegerDivFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivideFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_divideByZero() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 0, inputParameters);
		IntegerDivFunctionBehaviorExecution obj = new IntegerDivFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify empty list indicating invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivideFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_remainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 5, inputParameters);
		IntegerDivFunctionBehaviorExecution obj = new IntegerDivFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(2, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the IntegerDivideFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, -4, inputParameters);
		IntegerDivFunctionBehaviorExecution obj = new IntegerDivFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerDivideFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerDivideFunctionBehaviorExecution obj = new IntegerDivideFunctionBehaviorExecution();
		IntegerDivideFunctionBehaviorExecution newobj = (IntegerDivideFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
