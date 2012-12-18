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
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class StringConcatFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the StringConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertTwoStringsIntoParameterValueList("aaa", "bbb", inputParameters);
		StringConcatFunctionBehaviorExecution obj = new StringConcatFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("aaabbb", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyString() throws Exception {
		StringConversion.insertTwoStringsIntoParameterValueList("aaa", "", inputParameters);
		StringConcatFunctionBehaviorExecution obj = new StringConcatFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("aaa", StringConversion.extractStringFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the StringConcatFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringConcatFunctionBehaviorExecution obj = new StringConcatFunctionBehaviorExecution();
		StringConcatFunctionBehaviorExecution newobj = (StringConcatFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
