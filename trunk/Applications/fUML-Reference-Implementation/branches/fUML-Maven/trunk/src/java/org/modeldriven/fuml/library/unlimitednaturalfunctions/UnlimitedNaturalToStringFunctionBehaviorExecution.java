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

public class UnlimitedNaturalToStringFunctionBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {

		UnlimitedNaturalValue unv1 = (UnlimitedNaturalValue) inputParameters.getValue(0).values.getValue(0);
		Debug.println("[doBody] argument = " + unv1.value.naturalValue);
		
		// Perform the toString operation.  If value is -1, return "*"
		StringValue resultObj = new StringValue();
		resultObj.value = unv1.toString();
		resultObj.type = this.locus.factory.getBuiltInType("String");

		Debug.println("[doBody] Unlimited Natural ToString result = " + resultObj.value);
				
		// Add output to the outputParameters list
		LibraryFunctions.addValueToOutputList(resultObj, outputParameters);
	}
	
    @Override
    public fUML.Semantics.Classes.Kernel.Value new_() {
        return new UnlimitedNaturalToStringFunctionBehaviorExecution();
    }	

} // UnlimitedNaturalToStringFunctionBehaviorExecution
