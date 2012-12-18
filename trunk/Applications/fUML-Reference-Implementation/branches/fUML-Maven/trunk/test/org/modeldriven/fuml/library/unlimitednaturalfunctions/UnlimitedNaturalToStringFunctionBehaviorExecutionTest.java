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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class UnlimitedNaturalToStringFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the UnlimitedNaturalToStringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_int() throws Exception {
		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(9, inputParameters);
		UnlimitedNaturalToStringFunctionBehaviorExecution obj = new UnlimitedNaturalToStringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("9", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToStringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_unbounded() throws Exception {
		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(-1, inputParameters);
		UnlimitedNaturalToStringFunctionBehaviorExecution obj = new UnlimitedNaturalToStringFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals("*", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToStringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(-8, inputParameters);
		UnlimitedNaturalToStringFunctionBehaviorExecution obj = new UnlimitedNaturalToStringFunctionBehaviorExecution();
		try {
			// this should throw an exception
			obj.doBody(inputParameters, outputParameters);
			fail("expected exception not thrown");
		} catch (IllegalArgumentException e) {}
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalToStringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalToStringFunctionBehaviorExecution obj = new UnlimitedNaturalToStringFunctionBehaviorExecution();
		UnlimitedNaturalToStringFunctionBehaviorExecution newobj = (UnlimitedNaturalToStringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
