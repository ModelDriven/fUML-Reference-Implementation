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

package org.modeldriven.fuml.library.stringfunctions;

import org.modeldriven.fuml.library.LibraryTestSetup;
import org.modeldriven.fuml.library.integerfunctions.IntegerConversion;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class StringSubstringFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcdefghi", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(5, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("cde", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_singleChar() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("a", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("a", StringConversion.extractStringFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_entireString() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("abc", StringConversion.extractStringFromParameterValueList(outputParameters));
	}		
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_sameLowerUpper() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("b", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidLower_tooLow() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(0, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}		
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidLower_tooHigh() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("aaa", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());	
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_tooLow() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(0, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());		
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_tooHigh() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_higherLessThanLower() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the StringSubstringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringSubstringFunctionBehaviorExecution obj = new StringSubstringFunctionBehaviorExecution();
		StringSubstringFunctionBehaviorExecution newobj = (StringSubstringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
