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

public class IntegerMaxFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMax() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(18, 12, inputParameters);
		IntegerMaxFunctionBehaviorExecution obj = new IntegerMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(18, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMax() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(88, 99, inputParameters);
		IntegerMaxFunctionBehaviorExecution obj = new IntegerMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-5, 5, inputParameters);
		IntegerMaxFunctionBehaviorExecution obj = new IntegerMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerMaxFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerMaxFunctionBehaviorExecution obj = new IntegerMaxFunctionBehaviorExecution();
		IntegerMaxFunctionBehaviorExecution newobj = (IntegerMaxFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
