/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.Object_;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.ParameterList;

public class CallEventExecution extends Execution {

	public Object_ caller = null;
	public boolean callerSuspended = false;
	
	public boolean isCallerSuspended() {
		// Check if the caller is still suspended.
		// This is done in isolation from possible concurrent updates to this flag.
		
		_beginIsolation();
		boolean isSuspended = this.callerSuspended;
		Debug.println("[isCallerSuspended] operation = " + this.getOperation().name + 
				", callerSuspended = " + isSuspended);
		_endIsolation();
		
		return isSuspended;
	}
	
	public void setCallerSuspended(boolean callerSuspended) {
		// Set the caller suspended flag to the given value.
	    // This is done in isolation from possible concurrent queries to this flag.
		
		_beginIsolation();
		this.callerSuspended = callerSuspended;
		Debug.println("[setCallerSuspended] operation = " + this.getOperation().name + 
				", callerSuspended = " + callerSuspended);
		_endIsolation();
	}
	
	public void suspend(){
		// Suspend the caller until the caller is released.
		
		this.setCallerSuspended(true);
		
		do {
			this.wait_();
		} while(this.isCallerSuspended());
		
	}
	
	@Override
	public void execute() {
		// Send a new CallEventOccurrence to the target of this call
		// (which is the context of this execution) and suspend the
		// caller until the call is completed. Note that the
		// call will never be completed if the target is not an active
		// object, since then the object would then have no event
		// pool in which the event occurrence could be placed.
		
		Reference reference = new Reference();
		reference.referent = this.context;

		CallEventOccurrence eventOccurrence = new CallEventOccurrence();
		eventOccurrence.execution = this;
		eventOccurrence.sendTo(reference);
		this.suspend();		
	}
	
	public Operation getOperation() {
		// Return the operation being called by this call event execution.

		return ((CallEventBehavior)this.getBehavior()).operation;
	}
	
	public ParameterValueList getInputParameterValues(){
		// Return input parameter values for this execution
		
		ParameterValueList parameterValues = new ParameterValueList();
		for(int i=0; i < this.parameterValues.size(); i++){
			ParameterValue parameterValue = this.parameterValues.get(i);
			if(parameterValue.parameter.direction == ParameterDirectionKind.in
					| parameterValue.parameter.direction == ParameterDirectionKind.inout){
				parameterValues.addValue(parameterValue);
			}
		}
		return parameterValues;
	}

	public void setOutputParameterValues(ParameterValueList parameterValues) {
		// Set the output parameter values for this execution.
		
		ParameterList parameters = this.getBehavior().ownedParameter;
		int i = 1;
		int j = 1;
		while (i <= parameters.size()) {
            Parameter parameter = parameters.get(i-1);
            if (parameter.direction == ParameterDirectionKind.inout | 
            		parameter.direction == ParameterDirectionKind.out | 
            		parameter.direction == ParameterDirectionKind.return_ ) {
				ParameterValue parameterValue = parameterValues.get(j-1);
				parameterValue.parameter = parameter;
				this.setParameterValue(parameterValue);
				j = j + 1;
            }
			i = i + 1;
		}
	}
	
	@Override
	public Value new_() {
		// Create a new call event execution.
		
		return new CallEventExecution();
	}
	
	@Override
	public Value copy() {
		// Create a new call event execution that is a copy of this execution, with the
		// caller initially not suspended.
		
		CallEventExecution copy = (CallEventExecution)super.copy();
		copy.callerSuspended = false;
		return copy;
	}
	
	 public void wait_() {
		 // Wait for an indeterminate amount of time to allow other concurrent
		 // executions to proceed.
		 // [There is no further formal specification for this operation.]
		 
		 Debug.println(!ExecutionQueue.step(), "[wait] Stuck!");
	 }

}
