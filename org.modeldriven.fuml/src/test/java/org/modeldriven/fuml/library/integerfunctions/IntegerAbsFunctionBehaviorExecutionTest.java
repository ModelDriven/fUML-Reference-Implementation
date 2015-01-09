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

public class IntegerAbsFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerAbsFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerAbsFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_positiveNumber() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);
		doBody();
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the IntegerAbsFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_megativeNumber() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(-99, inputParameters);
		doBody();
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the new_() method in the IntegerAbsFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerAbsFunctionBehaviorExecution newobj = (IntegerAbsFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
