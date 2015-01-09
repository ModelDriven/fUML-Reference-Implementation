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

public class IntegerMinFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerMinFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMin() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(18, 24, inputParameters);
		doBody();
		assertEquals(18, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMin() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(88, 77, inputParameters);
		doBody();
		assertEquals(77, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-5, 5, inputParameters);
		doBody();
		assertEquals(-5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerMinFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerMinFunctionBehaviorExecution newobj = (IntegerMinFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
