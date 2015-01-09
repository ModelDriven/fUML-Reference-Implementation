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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

/**
 * Unit tests for the BooleanToStringFunctionBehaviorExecution class
 */
public class BooleanToStringFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		this.obj = new BooleanToStringFunctionBehaviorExecution();
	}

	/**
	 * Tests the doBody() method in the BooleanToStringFunctionBehaviorExecution
	 * class with input argument of true
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		BooleanConversion.insertOneBooleanIntoParameterValueList(true, inputParameters);
		doBody();
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
		doBody();
		assertEquals("false", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the new_() method in the BooleanToStringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanToStringFunctionBehaviorExecution newobj = (BooleanToStringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}
}
