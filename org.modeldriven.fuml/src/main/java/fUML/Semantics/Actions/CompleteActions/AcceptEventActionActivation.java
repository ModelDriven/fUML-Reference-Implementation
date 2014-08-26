
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import java.util.Iterator;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Actions.IntermediateActions.*;
import fUML.Semantics.Loci.*;

public class AcceptEventActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public fUML.Semantics.Actions.CompleteActions.AcceptEventActionEventAccepter eventAccepter = null;
	public boolean waiting = false;

	public void run() {
		// Create an event accepter and initialize waiting to false.

		super.run();

		this.eventAccepter = new AcceptEventActionEventAccepter();
		this.eventAccepter.actionActivation = this;

		this.waiting = false;
	} // run

	public void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// Register the event accepter for this accept event action activation
		// with the context object of the enclosing activity execution
		// and wait for an event to be accepted.

		Debug.println("[fire] Action " + this.node.name + "...");

		this.getExecutionContext().register(this.eventAccepter);
		this.waiting = true;
		this.firing = false;

		this.suspend();
	} // fire

	public boolean isReady() {
		// An accept event action activiation is ready to fire only if it is not
		// already waiting for an event.

		boolean ready;
		if (this.waiting) {
			ready = false;
		} else {
			ready = super.isReady();
		}

		return ready;
	} // isReady

	public void doAction() {
		// Do nothing. [This will never be called.]

		return;
	} // doAction

	public void accept(
			fUML.Semantics.CommonBehaviors.Communications.SignalInstance signalInstance) {
		// Accept a signal occurance for the given signal instance.
		// If the action does not unmarshall, then place the signal instance on
		// the result pin, if any.
		// If the action does unmarshall, then get the feature values of the
		// signal instance, and place the values for each feature on the
		// corresponding output pin.
		// Concurrently fire all output pins while offering a single control
		// token.
		// If there are no incoming edges, then re-register this accept event
		// action execution with the context object.

		AcceptEventAction action = (AcceptEventAction) (this.node);
		OutputPinList resultPins = action.result;

		Debug.println("[accept] action = " + action.name
				+ ", signalinstance = " + signalInstance);

		if (this.running) {
			if (!action.isUnmarshall) {
				ValueList result = new ValueList();
				result.addValue(signalInstance);
				if (resultPins.size() > 0) {
					this.putTokens(resultPins.getValue(0), result);
				}
			} else {
				FeatureValueList featureValues = signalInstance
						.getFeatureValues();
				for (int i = 0; i < featureValues.size(); i++) {
					FeatureValue featureValue = featureValues.getValue(i);
					OutputPin resultPin = resultPins.getValue(i);
					this.putTokens(resultPin, featureValue.values);
				}
			}

			this.sendOffers();

			this.waiting = false;

			Debug.println("[fire] Checking if " + this.node.name
					+ " should fire again...");
			this.receiveOffer();

			this.resume();
		}

	} // accept

	public boolean match(
			fUML.Semantics.CommonBehaviors.Communications.SignalInstance signalInstance) {
		// Return true if the given signal instance matches a trigger of the
		// accept action of this activation.

		AcceptEventAction action = (AcceptEventAction) (this.node);
		TriggerList triggers = action.trigger;
		Signal signal = signalInstance.type;

		boolean matches = false;
		int i = 1;
		while (!matches & i <= triggers.size()) {
			matches = ((SignalEvent) (triggers.getValue(i - 1).event)).signal == signal;
			i = i + 1;
		}

		return matches;
	} // match

	public void terminate() {
		// Terminate this action and unregister its event accepter.

		super.terminate();

		if (this.waiting) {
			this.getExecutionContext().unregister(this.eventAccepter);
			this.waiting = false;
		}
	} // terminate

} // AcceptEventActionActivation
