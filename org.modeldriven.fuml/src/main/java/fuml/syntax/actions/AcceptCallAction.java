/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.syntax.actions;

public class AcceptCallAction extends AcceptEventAction {

	public OutputPin returnInformation = null;
	
	public void setReturnInformation(OutputPin returnInformation) {
		super.addOutput(returnInformation);
		this.returnInformation = returnInformation;
	}
	
}
