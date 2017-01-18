/*
 * Copyright 2017 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Classes.Kernel;

import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class CallEventBehavior extends Behavior {
	
	public Operation operation = null;
	
	public void setOperation(Operation operation) {
		this.operation = operation;
		for(int i = 0; i < operation.ownedParameter.size(); i++){
			Parameter operationParameter = this.operation.ownedParameter.get(i);
			Parameter parameter = new Parameter();
			parameter.setName(operationParameter.name);
			parameter.setType(operationParameter.type);
			parameter.setLower(operationParameter.multiplicityElement.lower);
			parameter.setUpper(operationParameter.multiplicityElement.upper.naturalValue);
			parameter.setDirection(operationParameter.direction);
			this.addOwnedParameter(parameter);
		}
		this.isReentrant = true;		
	}

}
