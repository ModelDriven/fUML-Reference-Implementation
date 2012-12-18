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

public class UnlimitedNaturalMaxFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMax() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(18, 12, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(18, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMax() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 99, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(99, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 99, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(99, -1, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}		
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-5, -8, inputParameters);
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		try {
			// this should throw an exception
			obj.doBody(inputParameters, outputParameters);
			fail("expected exception not thrown");
		} catch (IllegalArgumentException e) {}
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalMaxFunctionBehaviorExecution obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
		UnlimitedNaturalMaxFunctionBehaviorExecution newobj = (UnlimitedNaturalMaxFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
