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

public class UnlimitedNaturalMaxFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new UnlimitedNaturalMaxFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstMax() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(18, 12, inputParameters);
		doBody();
		assertEquals(18, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondMax() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(88, 99, inputParameters);
		doBody();
		assertEquals(99, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_firstUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, 99, inputParameters);
		doBody();
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_secondUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(99, -1, inputParameters);
		doBody();
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_bothUnbounded() throws Exception {
		UnlimitedNaturalConversion.insertTwoUnlimitedNaturalsIntoParameterValueList(-1, -1, inputParameters);
		doBody();
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}		
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
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
	 * Tests the new_() method in the UnlimitedNaturalMaxFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalMaxFunctionBehaviorExecution newobj = (UnlimitedNaturalMaxFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
