/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Syntax.CommonBehaviors.Communications;

import fUML.Syntax.Classes.Kernel.Operation;

public class CallEvent extends Event {
	
	public Operation operation = null;
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
