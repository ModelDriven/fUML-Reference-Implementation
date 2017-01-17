/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Syntax.Actions.CompleteActions;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.InputPinList;
import fUML.Syntax.CommonBehaviors.Communications.Trigger;

public class ReplyAction extends Action {

	public Trigger replyToCall = null;
	public InputPinList replyValue = new InputPinList();
	public InputPin returnInformation = null;
	
	public void setReplyToCall(Trigger replyToCall) {
		this.replyToCall = replyToCall;
	}
	
	public void addReplyValue(InputPin replyValue) {
		super.addInput(replyValue);
		this.replyValue.addValue(replyValue);
	}
	
	public void setReturnInformation(InputPin returnInformation) {
		super.addInput(returnInformation);
		this.returnInformation = returnInformation;
	}
}
