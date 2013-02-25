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

package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryTestSetup;
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class BooleanToBooleanFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("true", inputParameters);
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	

	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_TRUE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("TRUE", inputParameters);
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("false", inputParameters);
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_FALSE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("FALSE", inputParameters);
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_ABCDE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("ABCDE", inputParameters);
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the BooleanToBooleanFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanToBooleanFunctionBehaviorExecution obj = new BooleanToBooleanFunctionBehaviorExecution();
		BooleanToBooleanFunctionBehaviorExecution newobj = (BooleanToBooleanFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
