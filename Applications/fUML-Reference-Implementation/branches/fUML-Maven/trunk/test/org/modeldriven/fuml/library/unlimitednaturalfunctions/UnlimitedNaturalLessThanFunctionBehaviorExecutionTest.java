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

package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import org.modeldriven.fuml.library.LibraryTestSetup;
import org.modeldriven.fuml.library.booleanfunctions.BooleanConversion;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class UnlimitedNaturalLessThanFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(9, 10, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 87, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_equal() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 88, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 12, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(1000, -1, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-5, -8, inputParameters);
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		try {
			// this should throw an exception
			obj.doBody(inputParameters, outputParameters);
			fail("expected exception not thrown");
		} catch (IllegalArgumentException e) {}
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalLessThanFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalLessThanFunctionBehaviorExecution obj = new UnlimitedNaturalLessThanFunctionBehaviorExecution();
		UnlimitedNaturalLessThanFunctionBehaviorExecution newobj = (UnlimitedNaturalLessThanFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
