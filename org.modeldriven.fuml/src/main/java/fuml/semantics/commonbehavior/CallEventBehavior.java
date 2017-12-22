/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.syntax.classification.Operation;
import fuml.syntax.classification.Parameter;
import fuml.syntax.commonbehavior.Behavior;

public class CallEventBehavior extends Behavior {
	
	public Operation operation = null;
	
	public void setOperation(Operation operation) {
		// Set the operation for this call event behavior and construct
		// the behavior signature based on the operation signature.
		
		this.operation = operation;
		for(int i = 0; i < operation.ownedParameter.size(); i++){
			Parameter operationParameter = operation.ownedParameter.get(i);
			Parameter parameter = new Parameter();
			parameter.name = operationParameter.name;
			parameter.type = operationParameter.type;
			parameter.multiplicityElement.lowerValue = 
					operationParameter.multiplicityElement.lowerValue;
			parameter.multiplicityElement.lower = 
					operationParameter.multiplicityElement.lower;
			parameter.multiplicityElement.upperValue = 
					operationParameter.multiplicityElement.upperValue;
			parameter.multiplicityElement.upper = 
					operationParameter.multiplicityElement.upper;
			parameter.direction = operationParameter.direction;
			parameter.owner = this;
			parameter.namespace = this;
			
			this.ownedElement.addValue(parameter);
			this.ownedMember.addValue(parameter);
			this.member.addValue(parameter);
			this.ownedParameter.addValue(parameter);
		}
		this.isReentrant = true;
		this.name = "CallEventBehavior";
		if (operation.name != null) {
			this.name = this.name + "(" + operation.name + ")";
		}
	}

}
