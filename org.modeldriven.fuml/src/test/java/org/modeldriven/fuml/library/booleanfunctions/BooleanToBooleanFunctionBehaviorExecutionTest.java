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

package org.modeldriven.fuml.library.booleanfunctions;

import org.modeldriven.fuml.library.LibraryTest;
import org.modeldriven.fuml.library.stringfunctions.StringConversion;

public class BooleanToBooleanFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		this.obj = new BooleanToBooleanFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_true() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("true", inputParameters);
		doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	

	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_TRUE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("TRUE", inputParameters);
		doBody();
		assertEquals(true, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_false() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("false", inputParameters);
		doBody();
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_FALSE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("FALSE", inputParameters);
		doBody();
		assertEquals(false, BooleanConversion.extractBooleanFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the BooleanToBooleanFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_ABCDE() throws Exception {
		StringConversion.insertOneStringIntoParameterValueList("ABCDE", inputParameters);
		doBody();
		// verify output list is empty due to invalid input
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the BooleanToBooleanFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		BooleanToBooleanFunctionBehaviorExecution newobj = (BooleanToBooleanFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
