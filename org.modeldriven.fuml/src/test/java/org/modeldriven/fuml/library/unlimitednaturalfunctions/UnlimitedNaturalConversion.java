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

package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

/**
 * Provides helper methods for the Boolean Function JUnit tests.
 */
public class UnlimitedNaturalConversion {
	
	/**
	 * Adds two UnlimitedNatural values to a ParameterValueList. This is a helper method
	 * for the JUnit tests.  Because UnlimitedNatural values are stored as integers,
	 * the args to this method are integers.
	 * 
	 * @param i1
	 *            first integer value to be stored as UnlimitedNatural
	 * @param i2
	 *            second integer value to be stored as UnlimitedNatural
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertTwoUnlimitedNaturalsIntoParameterValueList(int i1, int i2,
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

		// Create an UnlimitedNaturalValue object to add to the ValueList to hold the
		// actual value
		UnlimitedNaturalValue unlimitedNaturalVaue1 = new UnlimitedNaturalValue();
		unlimitedNaturalVaue1.value = new UnlimitedNatural();
		unlimitedNaturalVaue1.value.naturalValue = i1;
		valueList1.add(unlimitedNaturalVaue1);
		
		UnlimitedNaturalValue unlimitedNaturalVaue2 = new UnlimitedNaturalValue();
		unlimitedNaturalVaue2.value = new UnlimitedNatural();
		unlimitedNaturalVaue2.value.naturalValue = i2;
		valueList2.add(unlimitedNaturalVaue2);
	}		

	/**
	 * Adds one UnlimitedNatural value to a ParameterValueList. This is a helper method
	 * for the JUnit tests.  Because UnlimitedNatural values are stored as integers,
	 * the arg to this method is an integer.
	 * 
	 * @param i1
	 *            integer value to be stored as UnlimitedNatural
	 * @param parameters
	 *            a ParameterValueList
	 */
	public static void insertOneUnlimitedNaturalIntoParameterValueList(int i1,
			ParameterValueList parameters) {

		// For each argument, create a new ParameterValue and add to the 
		// ParameterValueList
		ParameterValue parameterValue1 = new ParameterValue();
		parameters.add(parameterValue1);

		// Create a ValueList for Each ParameterValue
		ValueList valueList1 = new ValueList();
		parameterValue1.values = valueList1;

		// Create an UnlimitedNaturalValue object to add to the ValueList to hold the
		// actual value
		UnlimitedNaturalValue unlimitedNaturalVaue1 = new UnlimitedNaturalValue();
		unlimitedNaturalVaue1.value = new UnlimitedNatural();
		unlimitedNaturalVaue1.value.naturalValue = i1;
		valueList1.add(unlimitedNaturalVaue1);
	}
	
	/**
	 * Retrieves a single UnlimitedNatural value from the output parameter list and
	 * returns as an int. This is a helper method for the JUnit tests.
	 * 
	 * @param parameters
	 *            ParameterValueList
	 * @return int
	 */
	public static int extractUnlimitedNaturalFromParameterValueList(ParameterValueList parameters) {

		// Get ParameterValue object from the list
		ParameterValue pv = parameters.get(0);

		// Get UnlimitedNaturalValue from values list
		UnlimitedNaturalValue unv = (UnlimitedNaturalValue) pv.values.get(0);

		// Get the UnlimitedNatural and the int value
		UnlimitedNatural un = unv.value;		
		return un.naturalValue;
	}	
	
}
