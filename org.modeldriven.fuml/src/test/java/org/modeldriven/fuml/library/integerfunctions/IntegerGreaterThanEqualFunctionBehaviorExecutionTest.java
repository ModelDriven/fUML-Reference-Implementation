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
import org.modeldriven.fuml.library.booleanfunctions.BooleanConversion;

public class IntegerGreaterThanEqualFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerGreaterThanEqualFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(12, 11, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 20, inputParameters);
		doBody();
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_trueFromEquals() throws Exception {
		IntegerConversion.insertTwoIntegersIntoParameterValueList(10, 10, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the new_() method in the IntegerGreaterThanEqualFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerGreaterThanEqualFunctionBehaviorExecution newobj = (IntegerGreaterThanEqualFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
