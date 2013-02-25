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

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

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

    public Value new_() {
        return (Value) (new OperationExecution());
    }

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

    public void setParameterValue(String parameterName, fUML.Semantics.Classes.Kernel.Value value) {
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
