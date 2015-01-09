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

public class IntegerNegateFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerNegateFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerNegateFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_positive() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(10, inputParameters);
		doBody();
		assertEquals(-10, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerNegateFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(-5, inputParameters);
		doBody();
		assertEquals(5, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerNegateFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerNegateFunctionBehaviorExecution newobj = (IntegerNegateFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
