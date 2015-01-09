/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.unlimitednaturalfunctions;

import org.modeldriven.fuml.library.LibraryFunctions;

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public class UnlimitedNaturalToIntegerFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		// Get first UnlimitedNatural input argument
		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.getValue(0).values.getValue(0);
		UnlimitedNatural un1 = unv1.value;
		Debug.println("[doBody] argument = " + un1.naturalValue);
		
		// If the unlimited natural is unbounded (equal to -1), it cannot be
		// converted to an integer.  Return an empty values list.
		if (un1.naturalValue == -1) {
			Debug.println("[doBody] Unbounded input invalid for ToInteger function");
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			IntegerValue resultObj = new IntegerValue();
			resultObj.value = un1.naturalValue;
			resultObj.type = this.locus.factory.getBuiltInType("Integer");
			Debug.println("[doBody] Unlimited Natural ToInteger result = " + resultObj.value);
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
		}
	}
	
	@Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new UnlimitedNaturalToIntegerFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToIntegerFunctionBehaviorExecution
