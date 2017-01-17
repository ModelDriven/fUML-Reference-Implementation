/*
 * Copyright 2017 Data Access Technologies, Inc., except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.ParameterList;
import fUML.Syntax.CommonBehaviors.Communications.CallEvent;
import fUML.Syntax.CommonBehaviors.Communications.Trigger;

public class CallEventOccurrence extends EventOccurrence {
	
	public CallEventExecution execution = null;
	
	public Operation getOperation() {
		return this.execution.operation;
	}

	@Override
	public boolean match(Trigger trigger) {
		boolean matches = false;
		if (trigger.event instanceof CallEvent) {
			CallEvent callEvent = (CallEvent)trigger.event;
			matches = callEvent.operation == this.getOperation();
		}
		return matches;
	}

	@Override
	public ParameterValueList getParameterValues() {
		ParameterValueList parameterValues = new ParameterValueList();
		if (this.execution != null) {
			parameterValues = this.execution.getInputParameterValues();
		}			
		return parameterValues;
	}
	
	public void setOutputParameterValues(ParameterValueList parameterValues) {
		ParameterList parameters = this.execution.behavior.ownedParameter;
		int i = 1;
		int j = 1;
		while (i <= parameters.size()) {
            Parameter parameter = parameters.get(i-1);
            if (parameter.direction == ParameterDirectionKind.inout | 
            		parameter.direction == ParameterDirectionKind.out | 
            		parameter.direction == ParameterDirectionKind.return_ ) {
				ParameterValue parameterValue = parameterValues.get(j-1);
				parameterValue.parameter = parameter;
				this.execution.setParameterValue(parameterValue);
				j = j + 1;
            }
			i = i + 1;
		}
	}
	
	public void releaseCaller() {
		this.execution.setCallerSuspended(false);
	}

}
