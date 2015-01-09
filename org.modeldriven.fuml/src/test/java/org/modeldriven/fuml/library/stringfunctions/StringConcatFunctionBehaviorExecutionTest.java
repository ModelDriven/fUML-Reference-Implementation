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

public class StringConcatFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new StringConcatFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the StringConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertTwoStringsIntoParameterValueList("aaa", "bbb", inputParameters);
		doBody();
		assertEquals("aaabbb", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyString() throws Exception {
		StringConversion.insertTwoStringsIntoParameterValueList("aaa", "", inputParameters);
		doBody();
		assertEquals("aaa", StringConversion.extractStringFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the StringConcatFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringConcatFunctionBehaviorExecution newobj = (StringConcatFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
