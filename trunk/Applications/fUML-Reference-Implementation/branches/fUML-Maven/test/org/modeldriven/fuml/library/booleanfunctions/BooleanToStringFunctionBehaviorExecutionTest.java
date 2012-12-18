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

/**
 * Unit tests for the BooleanToStringFunctionBehaviorExecution class
 */
public class BooleanToStringFunctionBehaviorExecutionTest extends TestCase {

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
	 * Tests the doBody() method in the BooleanToStringFunctionBehaviorExecution
	 * class with input argument of true
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		BooleanConversion.insertOneBooleanIntoParameterValueList(true, inputParameters);
		BooleanToStringFunctionBehaviorExecution obj = new BooleanToStringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("true", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the BooleanToStringFunctionBehaviorExecution
	 * class with input argument of false
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		BooleanConversion.insertOneBooleanIntoParameterValueList(false, inputParameters);
		BooleanToStringFunctionBehaviorExecution obj = new BooleanToStringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("false", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the new_() method in the BooleanToStringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanToStringFunctionBehaviorExecution obj = new BooleanToStringFunctionBehaviorExecution();
		BooleanToStringFunctionBehaviorExecution newobj = (BooleanToStringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}
}
