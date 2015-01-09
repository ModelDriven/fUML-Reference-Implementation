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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

public class IntegerToIntegerFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerToIntegerFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("99", inputParameters);
		doBody();
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negative() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("-88", inputParameters);
		doBody();
		assertEquals(-88, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalid() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcd", inputParameters);
		doBody();
		// Verify that the values list is an empty list, since the input string is not a valid integer
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
		
	/**
	 * Tests the new_() method in the IntegerToIntegerFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerToIntegerFunctionBehaviorExecution newobj = (IntegerToIntegerFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
