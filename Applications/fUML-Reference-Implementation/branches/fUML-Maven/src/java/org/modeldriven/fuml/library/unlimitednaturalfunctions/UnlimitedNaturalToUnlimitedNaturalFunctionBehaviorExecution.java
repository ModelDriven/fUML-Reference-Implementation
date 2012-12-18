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
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public class UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	@Override
	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		// Get first StringValue input argument
		StringValue sv1 = (StringValue) inputParameters.getValue(0).values.getValue(0);
		String s = sv1.value;
		Debug.println("[doBody] argument = " + s);
		
		UnlimitedNatural un = new UnlimitedNatural();
		UnlimitedNaturalValue resultObj = new UnlimitedNaturalValue();
		resultObj.value = un;
		resultObj.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
		
		// If the String is value "*", it specifies unbounded.
		if (s.equals("*")) {
			un.naturalValue = -1;
			Debug.println("[doBody] Unlimited Natural ToUnlimitedNatural result = -1");			
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
			return;
		}
		
		// Convert String to integer.  This throws a NumberFormatException if the String
		// does not specify an integer
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			Debug.println("[doBody] Cannot be converted to an UnlimitedNatural: " + s);
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
			return;
		}
		
		// If the integer is less than -1, it cannot be converted to an UnlimitedNatural.
		// Return an empty values list
		if (i < 0) {
			Debug.println("[doBody] Cannot be converted to an UnlimitedNatural: " + i);
			LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
		} else {
			Debug.println("[doBody] Unlimited Natural ToUnlimitedNatural result = " + i);
			un.naturalValue = i;
			// Add output to the outputParameters list
			LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
		}
		
		return;
	}
	
    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToUnlimitedNaturalFunctionBehaviorExecution
