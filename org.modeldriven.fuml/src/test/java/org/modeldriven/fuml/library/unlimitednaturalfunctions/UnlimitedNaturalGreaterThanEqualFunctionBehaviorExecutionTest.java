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

package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import org.modeldriven.fuml.library.LibraryTest;
import org.modeldriven.fuml.library.booleanfunctions.BooleanConversion;

public class UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(9, 8, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 89, inputParameters);
		doBody();
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_equal() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 88, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 12, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(1000, -1, inputParameters);
		doBody();
		assertFalse(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		doBody();
		assertTrue(BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
//	/**
//	 * Tests the doBody() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
//	 * 
//	 * @throws Exception
//	 */
//	public void testDoBody_negativeNum() throws Exception {
//		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-5, -8, inputParameters);
//		try {
//			// this should throw an exception
//			doBody();
//			fail("expected exception not thrown");
//		} catch (IllegalArgumentException e) {}
//	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution newobj = (UnlimitedNaturalGreaterThanEqualFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
