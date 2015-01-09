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

public class IntegerDivFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerDivFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 4, inputParameters);
		doBody();
		assertEquals(3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_divideByZero() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 0, inputParameters);
		doBody();
		// Verify empty list indicating invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the doBody() method in the IntegerDivFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_remainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 5, inputParameters);
		doBody();
		assertEquals(2, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the IntegerDivFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, -4, inputParameters);
		doBody();
		assertEquals(-3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerDivFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerDivFunctionBehaviorExecution newobj = (IntegerDivFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
