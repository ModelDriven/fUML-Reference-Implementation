/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.Debug;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.syntax.classification.Operation;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;

public class CallEventExecution extends Execution {

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
	
	public void suspendCaller() {
		// Suspend the caller until the caller is released.
		
		while(this.isCallerSuspended()) {
			this.wait_();
		}
		
	}
	
	public void releaseCaller() {
		// Release the caller, if suspended.
		
		this.setCallerSuspended(false);
	}
	
	@Override
	public void execute() {
		// Make the call on the target object (which is the context of this execution) 
		// and suspend the caller until the call is completed. 
		
		// Note: The callerSuspended flag needs to be set before the call is made,
		// in case the call is immediately handled and returned, even before the
		// suspend loop is started.
		this.setCallerSuspended(true);
		
		this.makeCall();		
		this.suspendCaller();		
	}
	
	public void makeCall() {
		// Make the call on the target object (which is the context of this execution)
		// by sending a call event occurrence. (Note that the call will never be 
		// completed if the target is not an active object, since then the object 
		// would then have no event pool in which the event occurrence could be placed.)
		
		Reference reference = new Reference();
		reference.referent = this.context;		
		this.createEventOccurrence().sendTo(reference);		
	}
	
	public EventOccurrence createEventOccurrence() {
		// Create a call event occurrence associated with this call event execution.
		// (This operation may be overridden in subclasses to alter how the event
		// occurrence is create, e.g., if it is necessary to wrap it.)
		
		CallEventOccurrence eventOccurrence = new CallEventOccurrence();
		eventOccurrence.execution = this;
		return eventOccurrence;
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
