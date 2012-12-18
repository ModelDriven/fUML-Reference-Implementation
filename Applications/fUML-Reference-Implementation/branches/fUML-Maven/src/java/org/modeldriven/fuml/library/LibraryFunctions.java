/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.library;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class LibraryFunctions {

	/**
	 * Add the output value to the output ParameterValueList
	 * 
	 * @param parameterValue
	 * @param outputParamters
	 */
	public static void addValueToOutputList(Value value, ParameterValueList outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		ParameterValue parameterValue = outputParameters.getValue(0);

		// Create a new ValueList and add the value that is passed in as an argument
		ValueList valueList = new ValueList();
		valueList.add(value);
		
		// Connect the ParameterValue list to the ParameterValue
		parameterValue.values = valueList;		
	}
	
	/**
	 * Adds an empty values list to the output ParameterValueList.  This done when there is an
	 * error condition which should result in no values.
	 * 
	 * @param outputParameters
	 */
	public static void addEmptyValueListToOutputList(ParameterValueList outputParameters) {
		
		// The ParameterValue has already been created and added to the ParameterValueList.
		// Retrieve the first ParameterValue element in this list.
		ParameterValue parameterValue = outputParameters.getValue(0);

		// Create a new ValueList and leave it empty
		ValueList valueList = new ValueList();
		
		// Connect the empty ParameterValue list to the ParameterValue
		parameterValue.values = valueList;
	}

} // LibraryFunctions
