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

public class IntegerMaxFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerMaxFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMax() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(18, 12, inputParameters);
		doBody();
		assertEquals(18, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMax() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(88, 99, inputParameters);
		doBody();
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(-5, 5, inputParameters);
		doBody();
		assertEquals(5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerMaxFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerMaxFunctionBehaviorExecution newobj = (IntegerMaxFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
