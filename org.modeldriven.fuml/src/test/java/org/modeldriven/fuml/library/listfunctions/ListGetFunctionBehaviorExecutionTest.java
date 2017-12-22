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

public class ListGetFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new ListGetFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the ListGetFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_oneElement() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] List with one element, get element");
		
		// Create a ValueList with one element
		ValueList vl = new ValueList();
		IntegerValue iv1 = new IntegerValue();
		iv1.value = 99;
		vl.addValue(iv1);
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);
		inputParameters.getValue(0).values = vl;
		
		// Specify list element to get as index value 1 (first item in list)
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);

		// Run get function on list
		doBody();
		
		// Get the returned Value object
		IntegerValue iv2 = (IntegerValue) outputParameters.getValue(0).values.getValue(0);		
		assertEquals(99, iv2.value);
	}
	
	/**
	 * Tests the doBody() method in the ListGetFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_manyElements() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] List with 99 elements, get 99th element");
		
		// Create a ValueList
		ValueList vl = new ValueList();
		
		for (int i=1; i<100 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);
		}
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);
		inputParameters.getValue(0).values = vl;
		
		// Specify list element to get as index value 99 (last item in list)
		IntegerConversion.insertOneIntegerIntoParameterValueList(99, inputParameters);

		// Run get function on list
		doBody();
		
		// Get the returned Value object
		IntegerValue iv2 = (IntegerValue) outputParameters.getValue(0).values.getValue(0);		
		assertEquals(99, iv2.value);
	}
	
	/**
	 * Tests the doBody() method in the ListGetFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_indexTooHigh() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] List with 1 element, get 2nd (nonexistent) element");
		
		// Create a ValueList with one element
		ValueList vl = new ValueList();
		IntegerValue iv1 = new IntegerValue();
		iv1.value = 100;
		vl.addValue(iv1);
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);
		inputParameters.getValue(0).values = vl;
		
		// Specify list element to get as index value 2 (second item in list), which
		// does not exist
		IntegerConversion.insertOneIntegerIntoParameterValueList(2, inputParameters);

		// Run get function on list
		doBody();
		
		// Assert the list is empty
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the doBody() method in the ListGetFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_invalidIndex() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] Specify invalid index of 0");
		
		// Create a ValueList with one element
		ValueList vl = new ValueList();
		IntegerValue iv1 = new IntegerValue();
		iv1.value = 99;
		vl.addValue(iv1);
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);
		inputParameters.getValue(0).values = vl;
		
		// Specify list element to get as index value 0, an invalid value
		IntegerConversion.insertOneIntegerIntoParameterValueList(0, inputParameters);

		// Run get function on list
		doBody();
		
		// Assert the list is empty due to invalid index
		assertEquals(0, outputParameters.getValue(0).values.size());
	}
	
	/**
	 * Tests the doBody() method in the ListGetFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyList() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] Empty list, get first (nonexistent) element");
		
		// Create an empty ValueList
		ValueList vl = new ValueList();
		
		// Put this list in the inputParameters argument
		ParameterValue pv = new ParameterValue();
		inputParameters.addValue(pv);		
		inputParameters.getValue(0).values = vl;
		
		// Specify list element to get as index value 1 (first item in list)
		IntegerConversion.insertOneIntegerIntoParameterValueList(1, inputParameters);
		
		// Run size function on list
		doBody();
		
		// Assert the list is empty due to invalid index
		assertEquals(0, outputParameters.getValue(0).values.size());	}
	
	/**
	 * Tests the new_() method in the ListGetFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		ListGetFunctionBehaviorExecution newobj = (ListGetFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
