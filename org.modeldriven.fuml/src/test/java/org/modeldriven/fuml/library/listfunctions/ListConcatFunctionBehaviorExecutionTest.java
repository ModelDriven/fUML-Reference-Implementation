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

import fuml.Debug;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.values.ValueList;

public class ListConcatFunctionBehaviorExecutionTest extends LibraryTest {
	
	@Override
	public void setUp() {
		super.setUp();
		obj = new ListConcatFunctionBehaviorExecution();
	}
	
	/**
	 * Tests the doBody() method in the ListConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_twoEmptyLists() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListConcat] Two empty lists");
		
		ParameterValue pv = new ParameterValue();
		pv.values = new ValueList();
		inputParameters.addValue(pv);
		
		pv = new ParameterValue();
		pv.values = new ValueList();
		inputParameters.addValue(pv);
		
		doBody();
		
		ValueList values = outputParameters.getValue(0).values;		
		assertTrue(values.isEmpty());
	}
	
	/**
	 * Tests the doBody() method in the ListConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_nonEmptyListAndEmptyList() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] List with 99 elements and empty list");
		
		ValueList vl = new ValueList();
		
		for (int i=1; i<100 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);
		}
		
		ParameterValue pv = new ParameterValue();
		pv.values = vl;
		inputParameters.addValue(pv);
		
		pv = new ParameterValue();
		pv.values = new ValueList();
		inputParameters.addValue(pv);

		doBody();
		
		ValueList values = outputParameters.getValue(0).values;
		assertEquals(99, values.size());
		for (int i=1; i<100; i++) {
			assertEquals(i, ((IntegerValue)values.get(i-1)).value);
		}
	}
	
	/**
	 * Tests the doBody() method in the ListConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_emptyListAndNonEmptyList() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] Empty list and list with 99 elements");
		
		ParameterValue pv = new ParameterValue();
		pv.values = new ValueList();
		inputParameters.addValue(pv);

		ValueList vl = new ValueList();
		
		for (int i=1; i<100 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);
		}
		
		pv = new ParameterValue();
		pv.values = vl;
		inputParameters.addValue(pv);
		
		doBody();
		
		ValueList values = outputParameters.getValue(0).values;
		assertEquals(99, values.size());
		for (int i=1; i<100; i++) {
			assertEquals(i, ((IntegerValue)values.get(i-1)).value);
		}
	}
	
	/**
	 * Tests the doBody() method in the ListConcatFunctionBehaviorExecution
	 * 
	 * @throws Exception
	 */
	public void testDoBody_twoNonEmptyLists() throws Exception {
		
		Debug.println("");
		Debug.println("[JUnit:ListGet] Two non-empty lists");
		
		ValueList vl = new ValueList();
		
		for (int i=1; i<50 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);
		}
		
		ParameterValue pv = new ParameterValue();
		pv.values = vl;
		inputParameters.addValue(pv);

		vl = new ValueList();
		
		for (int i=50; i<100 ; i++) {
			IntegerValue iv1 = new IntegerValue();
			iv1.value = i;
			vl.addValue(iv1);
		}
		
		pv = new ParameterValue();
		pv.values = vl;
		inputParameters.addValue(pv);
		
		doBody();
		
		ValueList values = outputParameters.getValue(0).values;
		assertEquals(99, values.size());
		for (int i=1; i<100; i++) {
			assertEquals(i, ((IntegerValue)values.get(i-1)).value);
		}
	}
	
	/**
	 * Tests the new_() method in the ListConcatFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		ListConcatFunctionBehaviorExecution newobj = (ListConcatFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
