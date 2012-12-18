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

public class IntegerNegateFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerNegateFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_positive() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(10, inputParameters);
		IntegerNegateFunctionBehaviorExecution obj = new IntegerNegateFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-10, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerNegateFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(-5, inputParameters);
		IntegerNegateFunctionBehaviorExecution obj = new IntegerNegateFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerNegateFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerNegateFunctionBehaviorExecution obj = new IntegerNegateFunctionBehaviorExecution();
		IntegerNegateFunctionBehaviorExecution newobj = (IntegerNegateFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
