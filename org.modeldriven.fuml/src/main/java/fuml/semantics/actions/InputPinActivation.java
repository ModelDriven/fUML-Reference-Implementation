
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

package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.activities.Token;
import fuml.semantics.activities.TokenList;
import fuml.semantics.commonbehavior.StreamingParameterValue;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.actions.Pin;

public class InputPinActivation extends fuml.semantics.actions.PinActivation {
	
	public StreamingParameterValue streamingParameterValue = null;

	public void receiveOffer() {
		// If this pin does not have a streaming parameter value reference, 
		// then forward the offer to the action activation. (When all input 
		// pins are ready, the action will fire them.)
		// If the pin has a streaming parameter value reference, then
		// accept offered tokens up to the multiplicity upper bound of the
		// pin and fire on the accepted tokens.

		if (this.streamingParameterValue == null) {
			this.actionActivation.receiveOffer();			
		} else {
			super.receiveOffer();
		}
	} // receiveOffer
	
	public void fire(TokenList incomingTokens) {
		// Add all incoming tokens to the pin.
		// If the pin has a streaming parameter value reference, and there
		// are incoming tokens, then post the values from the tokens to the
		// streaming parameter value. Then check if the streaming parameter
		// value has terminated and, if so, terminate the action activation.
		
		super.fire(incomingTokens);
		
		if (this.streamingParameterValue != null && incomingTokens.size() > 0) {
			ValueList values = new ValueList();
			for (int i = 0; i < incomingTokens.size(); i++) {
				Token token = incomingTokens.getValue(i);
				Value value = token.getValue();
				if (value != null) {
					values.addValue(value);
				}
			}
			this.streamingParameterValue.post(values);
			
			boolean isTerminated = false;
			
			_beginIsolation();
			isTerminated = this.streamingParameterValue.isTerminated();
			Debug.println("[isTerminated] isTerminated = " + isTerminated);
			_endIsolation();
			
			if (isTerminated) {
				this.actionActivation.terminate();
			}			
		}
	}

	public boolean isReady() {
		// Return true if the total number of values already being offered by
		// this pin plus those being offered by the sources of incoming edges
		// is at least equal to the minimum multiplicity of the pin.

		boolean ready = super.isReady();
		if (ready) {
			int totalValueCount = this.countUnofferedTokens()
					+ this.countOfferedValues();
			int minimum = ((Pin) this.node).multiplicityElement.lower;
			ready = totalValueCount >= minimum;
		}

		return ready;
	} // isReady
	
} // InputPinActivation
