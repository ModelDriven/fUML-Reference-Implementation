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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_int() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("9", inputParameters);
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(9, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_unbounded() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("*", inputParameters);
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("-5", inputParameters);
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify the values list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_notNumber() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		// Verify the values list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution newobj = (UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
