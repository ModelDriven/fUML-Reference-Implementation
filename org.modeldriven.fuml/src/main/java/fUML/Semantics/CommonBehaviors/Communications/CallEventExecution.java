/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class CallEventExecution extends Execution {

	// Called operation
	public Operation operation;
	
	// Behavior signature associated with the operation
	public Behavior behavior;
	
	// Object from which the call was emitted (possibly blocked)
	// public Object_ callerContext;
	
	public boolean callerSuspended = false;
	
	public boolean isCallerSuspended() {
		// Check if the caller is still suspended.
		// This is done in isolation from possible concurrent
		// updates to this flag;
		
		_beginIsolation();
		boolean isSuspended = this.callerSuspended;
		Debug.println("[isCallerSuspended] isSuspended = " + isSuspended);
		_endIsolation();
		return isSuspended;
	}
	
	public void setCallerSuspended(boolean callerSuspended) {
		// Set the caller suspended flag to the given value.
	    // This is done in isolation from possible concurrent
		// queries to this flag.
		
		_beginIsolation();
		this.callerSuspended = callerSuspended;
		Debug.println("[setCallerSuspended] callSuspended = " + callerSuspended);
		_endIsolation();
	}
	
	public void suspend(){
		// Suspend the caller until the caller is released.
		
		this.setCallerSuspended(true);
		
		while(this.isCallerSuspended()) {
			ExecutionQueue.step();
		}
		
	}
	
	@Override
	public void execute() {
		// Send a new CallEventOccurrence to the target of this call
		// (which is the context of this execution) and suspend the
		// caller until the call is completed. Note that the
		// call will never be completed if the target is not an active
		// object, since then the object would then have no event
		// pool in which the event occurrence could be placed.

		CallEventOccurrence eventOccurrence = new CallEventOccurrence();
		eventOccurrence.execution = this;
		this.context.send(eventOccurrence);
		this.suspend();		
	}
	
	@Override
	public Behavior getBehavior() {
		// Compute a behavior signature corresponding to the operation
		if(this.behavior == null){
			this.behavior = new OpaqueBehavior();
			for(int i = 0; i < this.operation.ownedParameter.size(); i++){
				Parameter operationParameter = this.operation.ownedParameter.get(i);
				Parameter parameter = new Parameter();
				parameter.setName(operationParameter.name);
				parameter.setType(operationParameter.type);
				parameter.setLower(operationParameter.multiplicityElement.lower);
				parameter.setUpper(operationParameter.multiplicityElement.upper.naturalValue);
				parameter.setDirection(operationParameter.direction);
				this.behavior.addOwnedParameter(parameter);
			}
		}
		return this.behavior;
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

	@Override
	public Value new_() {
		return new CallEventExecution();
	}
	
	@Override
	public Value copy() {
		CallEventExecution copy = (CallEventExecution)super.copy();
		copy.operation = this.operation;
		copy.behavior = this.behavior;
		copy.callerSuspended = false;
		return copy;
	}
	
}
