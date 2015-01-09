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

public class IntegerToStringFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerToStringFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerToStringFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);
		doBody();
		assertEquals("99", StringConversion.extractStringFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the new_() method in the IntegerToStringFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerToStringFunctionBehaviorExecution newobj = (IntegerToStringFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
