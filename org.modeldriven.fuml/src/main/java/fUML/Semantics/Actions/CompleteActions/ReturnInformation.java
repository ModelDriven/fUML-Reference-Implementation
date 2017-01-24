/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.CompleteActions;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.CommonBehaviors.Communications.CallEventOccurrence;
import fUML.Syntax.Classes.Kernel.ClassifierList;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class ReturnInformation extends Value {
	
	public CallEventOccurrence callEventOccurrence = null;
	
	public Operation getOperation() {
		// Return the operation associated with the call event occurrence of this
		// return information.

		return this.callEventOccurrence.getOperation();
	}
	
	public void reply(ParameterValueList outputParameterValues) {
		// Reply to the call by setting the output parameters and
		// releasing the caller.
		
		this.callEventOccurrence.setOutputParameterValues(outputParameterValues);
		this.callEventOccurrence.releaseCaller();
	}

	@Override
	public ValueSpecification specify() {
		// Return information cannot be specified using a value specification.
		
		return null;
	}

	@Override
	public ClassifierList getTypes() {
		// Return information is untyped.
		
		return new ClassifierList();
	}
	
	@Override
	public boolean equals(Value otherValue) {
		// One return information value equals another if they are for the
		// same call event occurrence.
		
		boolean isEqual = false;
		
		if (otherValue instanceof ReturnInformation) {
			isEqual = ((ReturnInformation)otherValue).callEventOccurrence == 
					this.callEventOccurrence;
		}
		
		return isEqual;
	}

	@Override
	protected Value new_() {
		// Create a new return information value, with an empty call event occurrence.
		
		return new ReturnInformation();
	}
	
	@Override
	public Value copy() {
		// Create a new return information value that is a copy of this value, with
		// the same call event occurrence.
		
		ReturnInformation copy = (ReturnInformation)super.copy();
		copy.callEventOccurrence = this.callEventOccurrence;
		return copy;
	}

	@Override
	public String toString() {
		// Return a string representation of the return information.
		
		return "ReturnInformation(" + callEventOccurrence.getOperation().name + ")";
	}

}
