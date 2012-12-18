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
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class IntegerMinFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMin() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(18, 24, inputParameters);
		IntegerMinFunctionBehaviorExecution obj = new IntegerMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(18, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMin() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(88, 77, inputParameters);
		IntegerMinFunctionBehaviorExecution obj = new IntegerMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(77, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-5, 5, inputParameters);
		IntegerMinFunctionBehaviorExecution obj = new IntegerMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerMinFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerMinFunctionBehaviorExecution obj = new IntegerMinFunctionBehaviorExecution();
		IntegerMinFunctionBehaviorExecution newobj = (IntegerMinFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
