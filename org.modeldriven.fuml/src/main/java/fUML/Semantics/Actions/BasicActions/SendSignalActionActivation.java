/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.BasicActions;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Actions.BasicActions.*;

import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.Communications.*;

public class SendSignalActionActivation extends
		fUML.Semantics.Actions.BasicActions.InvocationActionActivation {

	public void doAction() {
		// Get the value from the target pin. If the value is not a reference,
		// then do nothing.
		// Otherwise, construct a signal using the values from the argument pins
		// and send it to the referent object.

		SendSignalAction action = (SendSignalAction) (this.node);
		Value target = this.takeTokens(action.target).getValue(0);

		if (target instanceof Reference) {
			Signal signal = action.signal;

			SignalInstance signalInstance = new SignalInstance();
			signalInstance.type = signal;

			PropertyList attributes = signal.ownedAttribute;
			InputPinList argumentPins = action.argument;
			for (int i = 0; i < attributes.size(); i++) {
				Property attribute = attributes.getValue(i);
				InputPin argumentPin = argumentPins.getValue(i);
				ValueList values = this.takeTokens(argumentPin);
				signalInstance.setFeatureValue(attribute, values, 0);
			}

			SignalEventOccurrence signalEventOccurrence = new SignalEventOccurrence();
			signalEventOccurrence.signalInstance = (SignalInstance) signalInstance.copy();
			signalEventOccurrence.sendTo((Reference)target);
		}
	} // doAction

} // SendSignalActionActivation
