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
import org.modeldriven.fuml.library.integerfunctions.IntegerConversion;

public class UnlimitedNaturalToIntegerFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new UnlimitedNaturalToIntegerFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_int() throws Exception {
		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(9, inputParameters);
		doBody();
		assertEquals(9, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_unbounded() throws Exception {
		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(-1, inputParameters);
		doBody();
		// Verify the values list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
//	public void testDoBody_negativeNum() throws Exception {
//		UnlimitedNaturalConversion.insertOneUnlimitedNaturalIntoParameterValueList(-8, inputParameters);
//		try {
//			// this should throw an exception
//			doBody();
//			fail("expected exception not thrown");
//		} catch (IllegalArgumentException e) {}
//	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalToIntegerFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalToIntegerFunctionBehaviorExecution newobj = (UnlimitedNaturalToIntegerFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
