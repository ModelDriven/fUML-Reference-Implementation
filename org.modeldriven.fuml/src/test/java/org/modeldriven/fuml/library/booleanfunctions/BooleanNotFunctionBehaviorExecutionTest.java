/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryTest;

/**
 * Unit tests for the BooleanToStringFunctionBehaviorExecution class
 */
public class BooleanNotFunctionBehaviorExecutionTest extends LibraryTest {

	@Override
	public void setUp() {
		super.setUp();
		this.obj = new BooleanNotFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the BooleanToStringFunctionBehaviorExecution
	 * class with input argument of false
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		BooleanConversion.insertOneBooleanIntoParameterValueList(true, inputParameters);
		this.doBody();
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the BooleanNotFunctionBehaviorExecution
	 * class with input argument of true
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		BooleanConversion.insertOneBooleanIntoParameterValueList(false, inputParameters);
		this.doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the new_() method in the BooleanNotFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanNotFunctionBehaviorExecution newobj = (BooleanNotFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}
}
