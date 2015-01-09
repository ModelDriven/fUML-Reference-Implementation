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

public class IntegerModFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerModFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_remainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 3, inputParameters);
		doBody();
		assertEquals(1, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_noRemainder() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 3, inputParameters);
		doBody();
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the IntegerModFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_zeroSecondArg() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 0, inputParameters);
		doBody();
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the new_() method in the IntegerModFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerModFunctionBehaviorExecution newobj = (IntegerModFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
