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

public class StringSizeFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the StringSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("aaa", inputParameters);
		StringSizeFunctionBehaviorExecution obj = new StringSizeFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyString() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("", inputParameters);
		StringSizeFunctionBehaviorExecution obj = new StringSizeFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the StringSizeFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringSizeFunctionBehaviorExecution obj = new StringSizeFunctionBehaviorExecution();
		StringSizeFunctionBehaviorExecution newobj = (StringSizeFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
