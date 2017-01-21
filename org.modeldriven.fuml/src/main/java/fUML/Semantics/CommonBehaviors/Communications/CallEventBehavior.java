/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

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
	}

}
