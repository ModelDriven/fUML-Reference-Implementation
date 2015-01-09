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

package org.modeldriven.fuml.library.stringfunctions;

import org.modeldriven.fuml.library.LibraryTest;
import org.modeldriven.fuml.library.integerfunctions.IntegerConversion;

public class StringSubstringFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new StringSubstringFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcdefghi", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(5, inputParameters);
		doBody();
		assertEquals("cde", StringConversion.extractStringFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_singleChar() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("a", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		doBody();
		assertEquals("a", StringConversion.extractStringFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_entireString() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		doBody();
		assertEquals("abc", StringConversion.extractStringFromParameterValueList(outputParameters));
	}		
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_sameLowerUpper() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		doBody();
		assertEquals("b", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidLower_tooLow() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(0, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(3, inputParameters);
		doBody();
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}		
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidLower_tooHigh() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("aaa", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		doBody();
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());	
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_tooLow() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(0, inputParameters);
		doBody();
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());		
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_tooHigh() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(4, inputParameters);
		doBody();
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the doBody() method in the StringSubstringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidUpper_higherLessThanLower() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abc", inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		doBody();
		// Verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the StringSubstringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringSubstringFunctionBehaviorExecution newobj = (StringSubstringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
