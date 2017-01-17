/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import fUML.Semantics.CommonBehaviors.Communications.CallEventOccurrence;
import fUML.Semantics.CommonBehaviors.Communications.ReturnInformation;
import fUML.Semantics.CommonBehaviors.Communications.EventOccurrence;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.CompleteActions.AcceptCallAction;

public class AcceptCallActionActivation extends AcceptEventActionActivation {
	
	public void accept(EventOccurrence eventOccurrence) {
		// Accept the given event occurrence, which must be a call event occurrence.
		// Place return information for the call on the return information
		// output pin. Then complete the acceptance of the event occurrence
		// as usual.
		
		AcceptCallAction action = (AcceptCallAction) this.node;
		OutputPin returnInformationPin = action.returnInformation;
		
		ReturnInformation returnInformation = new ReturnInformation();
		returnInformation.callEventOccurrence = (CallEventOccurrence) eventOccurrence;
		
		this.putToken(returnInformationPin, returnInformation);
		
		Debug.println("[accept] action = " + action.name + 
				", returnInformation = " + returnInformation);
		
		super.accept(eventOccurrence);
		
	}

}
