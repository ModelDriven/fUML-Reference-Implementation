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
import org.modeldriven.fuml.library.unlimitednaturalfunctions.UnlimitedNaturalConversion;

public class IntegerToUnlimitedNaturalFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new IntegerToUnlimitedNaturalFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);
		doBody();
		assertEquals(99, UnlimitedNaturalConversion.extractUnlimitedNaturalFromParameterValueList(outputParameters));
	}
		
	/**
	 * Tests the doBody() method in the IntegerToIntegerFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_negativeValue() throws Exception {
		IntegerConversion.insertOneIntegerIntoParameterValueList(-99, inputParameters);
		doBody();
		// Because of the invalid negative input arg, the output list should be empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}	
	
	/**
	 * Tests the new_() method in the IntegerToUnlimitedNaturalFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		IntegerToUnlimitedNaturalFunctionBehaviorExecution newobj = (IntegerToUnlimitedNaturalFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
