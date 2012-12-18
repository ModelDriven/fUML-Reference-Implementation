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

package org.modeldriven.fuml.library.stringfunctions;

import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

/**
 * Provides helper methods for the Boolean Function JUnit tests.
 */
public class StringConversion {

	/**
	 * Adds a String value to a ParameterValueList. This is a helper method for
	 * the JUnit tests.
	 * 
	 * @param s1
	 *            first String value
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertOneStringIntoParameterValueList(String s1,
			ParameterValueList parameters) {

		// For each argument, create a new ParameterValue and add to the 
		// ParameterValueList
		ParameterValue parameterValue1 = new ParameterValue();
		parameters.add(parameterValue1);

		// Create a ValueList for Each ParameterValue
		ValueList valueList1 = new ValueList();
		parameterValue1.values = valueList1;

		// Create a StringValue object to add to the ValueList to hold the
		// actual value
		StringValue stringValue1 = new StringValue();
		stringValue1.value = s1;
		valueList1.add(stringValue1);
	}	
	
	/**
	 * Adds two String values to a ParameterValueList. This is a helper method for
	 * the JUnit tests.
	 * 
	 * @param s1
	 *            first String value
	 * @param s2
	 *            second String value
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertTwoStringsIntoParameterValueList(String s1,
			String s2, ParameterValueList parameters) {

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

		// Create a StringValue object to add to the ValueList to hold the
		// actual value
		StringValue stringValue1 = new StringValue();
		stringValue1.value = s1;
		valueList1.add(stringValue1);
		StringValue stringValue2 = new StringValue();
		stringValue2.value = s2;
		valueList2.add(stringValue2);
	}		

	/**
	 * Retrieves a single String value from the output parameter list. This is a
	 * helper method for the JUnit tests.
	 * 
	 * @param parameters
	 *            ParameterValueList
	 * @return boolean
	 */
	public static String extractStringFromParameterValueList(ParameterValueList parameters) {

		// Get ParameterValue object from the list
		ParameterValue pv = parameters.get(0);

		// Get StringValue from values list
		StringValue sv = (StringValue) pv.values.get(0);

		return sv.value;
	}

}
