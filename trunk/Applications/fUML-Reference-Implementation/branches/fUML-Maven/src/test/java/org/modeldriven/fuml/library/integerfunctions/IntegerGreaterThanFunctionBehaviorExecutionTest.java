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

public class IntegerGreaterThanFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerGreaterThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 11, inputParameters);
		IntegerGreaterThanFunctionBehaviorExecution obj = new IntegerGreaterThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerGreaterThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 20, inputParameters);
		IntegerGreaterThanFunctionBehaviorExecution obj = new IntegerGreaterThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerGreaterThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_falseFromEquals() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 10, inputParameters);
		IntegerGreaterThanFunctionBehaviorExecution obj = new IntegerGreaterThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the new_() method in the IntegerGreaterThanFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerGreaterThanFunctionBehaviorExecution obj = new IntegerGreaterThanFunctionBehaviorExecution();
		IntegerGreaterThanFunctionBehaviorExecution newobj = (IntegerGreaterThanFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
