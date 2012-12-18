/*
 * Copyright (c) 2008 Lockheed Martin Corporation.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.listfunctions;

import org.modeldriven.fuml.library.LibraryTestSetup;
import org.modeldriven.fuml.library.integerfunctions.IntegerConversion;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.TestCase;

public class ListSizeFunctionBehaviorExecutionTest extends TestCase {
	
	ParameterValueList inputParameters;
	ParameterValueList outputParameters;

	/**
	 * Set up the ParameterValueLists to simulate the fUML system before calling
	 * the doBody() method on the library classes.
	 */
	@Override
	public void setUp() {
		LibraryTestSetup libraryTestSetup = new LibraryTestSetup();
		inputParameters = libraryTestSetup.setupInputParameterList();
		outputParameters = libraryTestSetup.setupOutputParameterList();
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
		ListSizeFunctionBehaviorExecution obj = new ListSizeFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
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
		ListSizeFunctionBehaviorExecution obj = new ListSizeFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
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
		ListSizeFunctionBehaviorExecution obj = new ListSizeFunctionBehaviorExecution();
		obj.doBody(inputParameters, outputParameters);
		assertEquals(0, IntegerConversion.extractIntegerFromParameterValueList(outputParameters));
	}
	
	/**
	 * Tests the new_() method in the ListSizeFunctionBehaviorExecution
	 * class
	 * 
	 * @throws Exception
	 */
	public void testNew_() throws Exception {
		ListSizeFunctionBehaviorExecution obj = new ListSizeFunctionBehaviorExecution();
		ListSizeFunctionBehaviorExecution newobj = (ListSizeFunctionBehaviorExecution) obj.new_();
		assertNotSame(obj, newobj);
	}	
}
