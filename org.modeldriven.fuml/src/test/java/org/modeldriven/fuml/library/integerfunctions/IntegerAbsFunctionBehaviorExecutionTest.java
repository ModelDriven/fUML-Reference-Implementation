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

public class IntegerAbsFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerAbsFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_positiveNumber() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);
		IntegerAbsFunctionBehaviorExecution obj = new IntegerAbsFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerAbsFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_megativeNumber() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(-99, inputParameters);
		IntegerAbsFunctionBehaviorExecution obj = new IntegerAbsFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerAbsFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerAbsFunctionBehaviorExecution obj = new IntegerAbsFunctionBehaviorExecution();
		IntegerAbsFunctionBehaviorExecution newobj = (IntegerAbsFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
