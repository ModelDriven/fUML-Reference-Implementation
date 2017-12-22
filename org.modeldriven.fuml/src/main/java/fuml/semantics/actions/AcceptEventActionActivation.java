
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

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.activities.ActivityNodeActivationGroup;
import fuml.semantics.classification.ValueList;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.commonbehavior.SignalEventOccurrence;
import fuml.semantics.simpleclassifiers.SignalInstance;
import fuml.syntax.actions.AcceptEventAction;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.OutputPinList;
import fuml.syntax.activities.ActivityNode;
import fuml.syntax.commonbehavior.TriggerList;

public class AcceptEventActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public fuml.semantics.actions.AcceptEventActionEventAccepter eventAccepter = null;
	public Boolean waiting = false;
	
	public void initialize(ActivityNode node, ActivityNodeActivationGroup group) {
		// Initialize this accept event action activation to be not waiting for an event.
		
		super.initialize(node, group);
		this.waiting = false;
	}

	public void run() {
		// Create an event accepter and initialize waiting to false.

		super.run();

		this.eventAccepter = new AcceptEventActionEventAccepter();
		this.eventAccepter.actionActivation = this;

		this.waiting = false;
	} // run

	public void fire(
			fuml.semantics.activities.TokenList incomingTokens) {
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
		// An accept event action activation is ready to fire only if it is not
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
			fuml.semantics.commonbehavior.EventOccurrence eventOccurrence) {
		// Accept the given event occurrence.
		// If the action does not unmarshall, then, if the event occurrence is
		// a signal event occurrence, place the signal instance of the signal
		// event occurrence on the result pin, if any.
		// If the action does unmarshall, then get the parameter values of the
		// event occurrence, and place the values for each parameter on the
		// corresponding output pin.
		// Concurrently fire all output pins while offering a single control
		// token.
		// If there are no incoming edges, then re-register this accept event
		// action execution with the context object.

		AcceptEventAction action = (AcceptEventAction) (this.node);
		OutputPinList resultPins = action.result;
		
		Debug.println("[accept] action = " + action.name + ", eventOccurrence = " + eventOccurrence);
		
		if (this.running) {
			if (!action.isUnmarshall) {
				if (eventOccurrence instanceof SignalEventOccurrence) {
					SignalInstance signalInstance = ((SignalEventOccurrence)eventOccurrence).signalInstance;
					Debug.println("[accept] isUnmarshall = false, signalInstance = " + signalInstance);			
					ValueList result = new ValueList();
					result.addValue(signalInstance);
					if (resultPins.size() > 0) {
						this.putTokens(resultPins.getValue(0), result);
					}
				}
			} else {
				ParameterValueList parameterValues = eventOccurrence.getParameterValues();
				for (int i = 0; i < parameterValues.size(); i++) {
					ParameterValue parameterValue = parameterValues.getValue(i);
					OutputPin resultPin = resultPins.getValue(i);
					this.putTokens(resultPin, parameterValue.values);
				}
			}

			this.sendOffers();

			this.waiting = false;

			Debug.println("[accept] Checking if " + this.node.name + " should fire again...");
			this.receiveOffer();

			this.resume();		
		}

	} // accept

	public boolean match(
			fuml.semantics.commonbehavior.EventOccurrence eventOccurrence) {
		// Return true if the given event occurrence matches a trigger of the
		// accept event action of this activation.

		AcceptEventAction action = (AcceptEventAction) (this.node);
		TriggerList triggers = action.trigger;
		
		return eventOccurrence.matchAny(triggers);
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
