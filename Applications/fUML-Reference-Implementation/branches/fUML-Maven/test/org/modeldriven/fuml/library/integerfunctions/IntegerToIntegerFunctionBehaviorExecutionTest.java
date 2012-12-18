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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class IntegerToIntegerFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("99", inputParameters);
		IntegerToIntegerFunctionBehaviorExecution obj = new IntegerToIntegerFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("-88", inputParameters);
		IntegerToIntegerFunctionBehaviorExecution obj = new IntegerToIntegerFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-88, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalid() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcd", inputParameters);
		IntegerToIntegerFunctionBehaviorExecution obj = new IntegerToIntegerFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify that the values list is an empty list, since the input string is not a valid integer
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
		
	/**
	 * Tests the new_() method in the IntegerToIntegerFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerToIntegerFunctionBehaviorExecution obj = new IntegerToIntegerFunctionBehaviorExecution();
		IntegerToIntegerFunctionBehaviorExecution newobj = (IntegerToIntegerFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
