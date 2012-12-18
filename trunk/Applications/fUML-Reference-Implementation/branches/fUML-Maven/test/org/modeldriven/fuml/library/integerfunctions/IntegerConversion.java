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

package org.modeldriven.fuml.library.integerfunctions;

import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

/**
 * Provides helper methods for the Boolean Function JUnit tests.
 */
public class IntegerConversion {
	
	/**
	 * Adds two integer values to a ParameterValueList. This is a helper method
	 * for the JUnit tests.
	 * 
	 * @param i1
	 *            first integer value
	 * @param i2
	 *            second integer value
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertTwoIntegersIntoParameterValueList(int i1, int i2,
			ParameterValueList parameters) {

		// For each argument, create a new ParameterValue and add to the 
		// ParameterValueList
		ParameterValue parameterValue1 = new ParameterValue();
		parameters.add(parameterValue1);
		ParameterValue parameterValue2 = new ParameterValue();
		parameters.add(parameterValue2);

		// Create a ValueList for Each ParameterValue
		ValueList valueList1 = new ValueList();
		parameterValue1.values = valueList1;
		ValueList valueList2 = new ValueList();
		parameterValue2.values = valueList2;

		// Create a IntegerValue object to add to the ValueList to hold the
		// actual value
		IntegerValue integerVaue1 = new IntegerValue();
		integerVaue1.value = i1;
		valueList1.add(integerVaue1);
		IntegerValue integerValue2 = new IntegerValue();
		integerValue2.value = i2;
		valueList2.add(integerValue2);
	}

	/**
	 * Adds an integer value to a ParameterValueList. This is a helper method for
	 * the JUnit tests.
	 * 
	 * @param i1
	 *            first integer value
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertOneIntegerIntoParameterValueList(int i1,
			ParameterValueList parameters) {

		// For each argument, create a new ParameterValue and add to the 
		// ParameterValueList
		ParameterValue parameterValue1 = new ParameterValue();
		parameters.add(parameterValue1);

		// Create a ValueList for Each ParameterValue
		ValueList valueList1 = new ValueList();
		parameterValue1.values = valueList1;

		// Create a IntegerValue object to add to the ValueList to hold the
		// actual value
		IntegerValue integerVaue1 = new IntegerValue();
		integerVaue1.value = i1;
		valueList1.add(integerVaue1);
	}	
	
	/**
	 * Retrieves a single integer value from the output parameter list. This is
	 * a helper method for the JUnit tests.
	 * 
	 * @param parameters
	 *            ParameterValueList
	 * @return int
	 */
	public static int extractIntegerFromParameterValueList(ParameterValueList parameters) {

		// Get ParameterValue object from the list
		ParameterValue pv = parameters.get(0);

		// Get IntegerValue from values list
		IntegerValue iv = (IntegerValue) pv.values.get(0);

		return iv.value;
	}	
	
}
