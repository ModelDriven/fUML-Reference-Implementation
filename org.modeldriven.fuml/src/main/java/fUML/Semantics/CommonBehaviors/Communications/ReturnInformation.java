/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.ClassifierList;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class ReturnInformation extends Value {
	
	public CallEventOccurrence callEventOccurrence = null;
	
	public void reply(ParameterValueList outputParameterValues) {
		this.callEventOccurrence.setOutputParameterValues(outputParameterValues);
		this.callEventOccurrence.releaseCaller();
	}

	@Override
	public ValueSpecification specify() {
		return null;
	}

	@Override
	public ClassifierList getTypes() {
		return new ClassifierList();
	}

	@Override
	protected Value new_() {
		return new ReturnInformation();
	}
	
	@Override
	public Value copy() {
		ReturnInformation copy = (ReturnInformation)super.copy();
		copy.callEventOccurrence = this.callEventOccurrence;
		return copy;
	}

	@Override
	public String toString() {
		return "ReturnInformation(" + callEventOccurrence.getOperation().name + ")";
	}

}
