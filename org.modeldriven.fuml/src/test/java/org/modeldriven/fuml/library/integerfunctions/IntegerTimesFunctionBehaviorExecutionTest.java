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

package org.modeldriven.fuml.library.integerfunctions;

import org.modeldriven.fuml.library.LibraryTest;

public class IntegerTimesFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerTimesFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerTimesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_zero() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(5, 0, inputParameters);
		doBody();
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerTimesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_pos() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(4, 4, inputParameters);
		doBody();
		assertEquals(16, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerTimesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_neg() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-2, 2, inputParameters);
		doBody();
		assertEquals(-4, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the new_() method in the IntegerTimesFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerTimesFunctionBehaviorExecution newobj = (IntegerTimesFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
