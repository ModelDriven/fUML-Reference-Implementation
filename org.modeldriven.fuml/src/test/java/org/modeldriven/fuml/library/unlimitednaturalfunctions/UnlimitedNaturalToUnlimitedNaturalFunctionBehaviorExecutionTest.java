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
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

public class UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_int() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("9", inputParameters);
		doBody();
		assertEquals(9, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_unbounded() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("*", inputParameters);
		doBody();
		assertEquals(-1, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeNum() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("-5", inputParameters);
		doBody();
		// Verify the values list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}

	/**
	 * Tests the doBody() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_notNumber() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("abcde", inputParameters);
		doBody();
		// Verify the values list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution newobj = (UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
