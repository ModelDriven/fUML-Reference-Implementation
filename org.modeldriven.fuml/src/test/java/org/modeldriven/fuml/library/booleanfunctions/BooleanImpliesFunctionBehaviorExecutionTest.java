/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryTest;

public class BooleanImpliesFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new BooleanImpliesFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the BooleanImpliesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true_true() throws Exception {
		BooleanConversion.insertTwoBooleansIntoParameterValueList(true, true, inputParameters);
		doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	

	/**
	 * Tests the doBody() method in the BooleanImpliesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true_false() throws Exception {
		BooleanConversion.insertTwoBooleansIntoParameterValueList(true, false, inputParameters);
		doBody();
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanImpliesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false_true() throws Exception {
		BooleanConversion.insertTwoBooleansIntoParameterValueList(false, true, inputParameters);
		doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanImpliesFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false_false() throws Exception {
		BooleanConversion.insertTwoBooleansIntoParameterValueList(false, false, inputParameters);
		doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the new_() method in the BooleanImpliesFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanImpliesFunctionBehaviorExecution newobj = (BooleanImpliesFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
