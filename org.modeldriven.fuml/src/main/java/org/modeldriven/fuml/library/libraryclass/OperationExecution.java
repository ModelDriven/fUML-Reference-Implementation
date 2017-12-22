/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * All modifications copyright 2009-2011 Data Access Technologies, Inc. 
 * Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */


package org.modeldriven.fuml.library.libraryclass;

import fuml.semantics.classification.Value;
import fuml.semantics.classification.ValueList;
import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.syntax.classification.Operation;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonbehavior.OpaqueBehavior;

public class OperationExecution extends Execution {

    public void set(ImplementationObject context, Operation operation) {
        this.context = context;

        OpaqueBehavior method = new OpaqueBehavior();
        method.specification = operation;

        for (Parameter operationParameter: operation.ownedParameter) {
            Parameter methodParameter = new Parameter();
            methodParameter.name = operationParameter.name;
            methodParameter.direction = operationParameter.direction;
            methodParameter.type = operationParameter.type;
            methodParameter.multiplicityElement = operationParameter.multiplicityElement;
            method.ownedParameter.addValue(methodParameter);
        }

        this.types.addValue(method);

    }

    @Override
    public Value new_() {
        return (Value) (new OperationExecution());
    }

    @Override
    public void execute() {
    	
    	// Note: The following ensures that this operation execution has
    	// output parameter values for all output parameters in the correct
    	// order. (Except inout parameters may still be out of order.)
    	for (Parameter parameter: this.getBehavior().ownedParameter){
    		if (parameter.direction == ParameterDirectionKind.out ||
    				parameter.direction == ParameterDirectionKind.return_) {
    			this.setParameterValue(parameter, new ValueList());
    		}
    	}
    	
        ((ImplementationObject) (this.context)).execute(this);
    }

    public String getOperationName() {
        return this.getBehavior().specification.name;
    }

    public Parameter getParameter(String parameterName) {
        Behavior method = this.getBehavior();

        for (Parameter parameter: method.ownedParameter) {
            if (parameter.name.equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public ParameterValue getParameterValue(
            String parameterName) {
        return this.getParameterValue(this.getParameter(parameterName));
    }
    
    public void setParameterValue(Parameter parameter, ValueList values) {
    	if (parameter != null) {
	        ParameterValue parameterValue = new ParameterValue();
	        parameterValue.parameter = parameter;
	        parameterValue.values = values;
	
	        this.setParameterValue(parameterValue);
    	}
    }

    public void setParameterValue(String parameterName, ValueList values) {
        this.setParameterValue(this.getParameter(parameterName), values);
    }

    public void setParameterValue(String parameterName, fuml.semantics.classification.Value value) {
        ValueList valueList = new ValueList();
        valueList.addValue(value);
        this.setParameterValue(parameterName, valueList);
    }
    
    public void setReturnParameterValue(ValueList values) {
        Behavior method = this.getBehavior();

        for (Parameter parameter: method.ownedParameter) {
            if (parameter.direction == ParameterDirectionKind.return_) {
                this.setParameterValue(parameter, values);
                return;
            }
        }
    }
    
    public void setReturnParameterValue(Value value) {
        ValueList valueList = new ValueList();
        valueList.addValue(value);
        this.setReturnParameterValue(valueList);
    }

} // OperationExecution
