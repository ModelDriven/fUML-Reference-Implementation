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

public class IntegerPlusFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerPlusFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_zero() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-2, 2, inputParameters);
		IntegerPlusFunctionBehaviorExecution obj = new IntegerPlusFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerPlusFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_pos() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(2, 2, inputParameters);
		IntegerPlusFunctionBehaviorExecution obj = new IntegerPlusFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(4, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerPlusFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_neg() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-2, -2, inputParameters);
		IntegerPlusFunctionBehaviorExecution obj = new IntegerPlusFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-4, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the new_() method in the IntegerPlusFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerPlusFunctionBehaviorExecution obj = new IntegerPlusFunctionBehaviorExecution();
		IntegerPlusFunctionBehaviorExecution newobj = (IntegerPlusFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
