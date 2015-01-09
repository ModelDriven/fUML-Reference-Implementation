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

public class IntegerMinusFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerMinusFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerMinusFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 10, inputParameters);
		doBody();
		assertEquals(2, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerMinusFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(5, 10, inputParameters);
		doBody();
		// Verify empty list indicating invalid input
		assertEquals(-5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerMinusFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerMinusFunctionBehaviorExecution newobj = (IntegerMinusFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
