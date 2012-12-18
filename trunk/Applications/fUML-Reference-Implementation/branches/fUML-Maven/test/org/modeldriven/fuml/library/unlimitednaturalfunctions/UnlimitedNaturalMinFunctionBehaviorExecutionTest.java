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
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class UnlimitedNaturalMinFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMin() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(9, 12, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(9, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMin() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 77, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(77, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 12, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(12, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(1000, -1, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(1000, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-5, -8, inputParameters);
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		try {
			// this should throw an exception
			obj.doBody(inputParameters, outputParameters);
			fail("expected exception not thrown");
		} catch (IllegalArgumentException e) {}
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalMinFunctionBehaviorExecution obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
		UnlimitedNaturalMinFunctionBehaviorExecution newobj = (UnlimitedNaturalMinFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
