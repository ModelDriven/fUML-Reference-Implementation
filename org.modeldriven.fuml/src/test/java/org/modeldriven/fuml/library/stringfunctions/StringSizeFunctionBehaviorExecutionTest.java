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

public class StringSizeFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new StringSizeFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the StringSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("aaa", inputParameters);
		doBody();
		assertEquals(3, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the StringSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyString() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("", inputParameters);
		doBody();
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the StringSizeFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		StringSizeFunctionBehaviorExecution newobj = (StringSizeFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
