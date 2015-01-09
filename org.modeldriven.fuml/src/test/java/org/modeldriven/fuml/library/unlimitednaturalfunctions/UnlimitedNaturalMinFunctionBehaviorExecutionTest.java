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

public class UnlimitedNaturalMinFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new UnlimitedNaturalMinFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMin() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(9, 12, inputParameters);
		doBody();
		assertEquals(9, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMin() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 77, inputParameters);
		doBody();
		assertEquals(77, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 12, inputParameters);
		doBody();
		assertEquals(12, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(1000, -1, inputParameters);
		doBody();
		assertEquals(1000, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		doBody();
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
//	public void testDoBody_negativeNum() throws Exception {
//		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-5, -8, inputParameters);
//		try {
//			// this should throw an exception
//			doBody();
//			fail("expected exception not thrown");
//		} catch (IllegalArgumentException e) {}
//	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalMinFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalMinFunctionBehaviorExecution newobj = (UnlimitedNaturalMinFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
