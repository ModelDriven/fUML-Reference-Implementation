/*
 * Copyright 2017 Data Access Technologies. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.commonbehavior.CallEventOccurrence;
import fuml.semantics.commonbehavior.EventOccurrence;
import fuml.syntax.actions.AcceptCallAction;
import fuml.syntax.actions.OutputPin;

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
