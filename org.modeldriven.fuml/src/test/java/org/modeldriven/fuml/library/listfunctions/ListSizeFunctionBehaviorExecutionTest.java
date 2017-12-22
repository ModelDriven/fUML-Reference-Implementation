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

package org.modeldriven.fuml.library.listfunctions;

import org.modeldriven.fuml.library.LibraryTest;
import org.modeldriven.fuml.library.integerfunctions.IntegerConversion;

import fuml.Debug;
import fuml.semantics.classification.ValueList;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.syntax.simpleclassifiers.IntegerValue;

public class ListSizeFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new ListSizeFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the ListSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_oneElement() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListSize] List with one element");
		
		// Create a ValueList with one element
		ValueList vl = new ValueList();
		IntegerValue iv1 = new IntegerValue();
		iv1.value = 99;
		vl.addValue(iv1);
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);
		inputParameters.getValue(0).values = vl;
		
		// Run size function on list
		doBody();
		assertEquals(1, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the doBody() method in the ListSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_manyElements() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListSize] List with 99 elements");
		
		// Create a ValueList
		ValueList vl = new ValueList();
		
		// Add many elements to this list
		for (int i=0 ; i<99 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);	
		}
				
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);		
		inputParameters.getValue(0).values = vl;
		
		// Run size function on list
		doBody();
		assertEquals(99, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}	
	
	/**
	 * Tests the doBody() method in the ListSizeFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyList() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListSize] Empty list");
		
		// Create an empty ValueList
		ValueList vl = new ValueList();
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);		
		inputParameters.getValue(0).values = vl;
		
		// Run size function on list
		doBody();
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the new_() method in the ListSizeFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		ListSizeFunctionBehaviorExecution newobj = (ListSizeFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
