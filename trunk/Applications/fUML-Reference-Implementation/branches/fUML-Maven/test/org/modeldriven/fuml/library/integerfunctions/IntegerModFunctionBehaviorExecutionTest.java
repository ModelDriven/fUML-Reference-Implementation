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

public class IntegerModFunctionBehaviorExecutionTest extends TestCase {
	
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
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_remainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 3, inputParameters);
		IntegerModFunctionBehaviorExecution obj = new IntegerModFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(1, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_noRemainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 3, inputParameters);
		IntegerModFunctionBehaviorExecution obj = new IntegerModFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_zeroSecondArg() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 0, inputParameters);
		IntegerModFunctionBehaviorExecution obj = new IntegerModFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the new_() method in the IntegerModFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerModFunctionBehaviorExecution obj = new IntegerModFunctionBehaviorExecution();
		IntegerModFunctionBehaviorExecution newobj = (IntegerModFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
