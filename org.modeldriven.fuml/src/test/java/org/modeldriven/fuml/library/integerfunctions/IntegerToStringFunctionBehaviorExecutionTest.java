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

public class IntegerToStringFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerToStringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);
		IntegerToStringFunctionBehaviorExecution obj = new IntegerToStringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("99", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the new_() method in the IntegerToStringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerToStringFunctionBehaviorExecution obj = new IntegerToStringFunctionBehaviorExecution();
		IntegerToStringFunctionBehaviorExecution newobj = (IntegerToStringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
