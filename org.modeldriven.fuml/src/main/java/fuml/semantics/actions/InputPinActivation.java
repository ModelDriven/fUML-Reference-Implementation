
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
		// If this pin activation is streaming, then accept offered tokens 
		// up to the multiplicity upper bound of the pin and fire on the 
		// accepted tokens.
		// If the pin activation is not streaming, then forward the offer 
		// to the action activation. (When all input pins are ready, the 
		// action will fire them.)

		if (this.isStreaming()) {
			super.receiveOffer();
		} else {
			this.actionActivation.receiveOffer();			
		}
	} // receiveOffer
	
	public void fire(TokenList incomingTokens) {
		// Add all incoming tokens to the pin.
		// If the pin activation is streaming, and there are incoming tokens, 
		// then post the values from the tokens to the streaming parameter value. 
		// Then check if the streaming parameter value has terminated and, if so, 
		// terminate the action activation.
		
		super.fire(incomingTokens);
		
		if (this.isStreaming() && incomingTokens.size() > 0) {
			ValueList values = new ValueList();
			for (int i = 0; i < incomingTokens.size(); i++) {
				Token token = incomingTokens.getValue(i);
				Value value = token.getValue();
				if (value != null) {
					values.addValue(value);
				}
			}
			this.streamingParameterValue.post(values);
			
			if (this.streamingIsTerminated()) {
				if (this.actionActivation instanceof CallActionActivation) {
					((CallActionActivation)this.actionActivation).completeStreamingCall();
				}
			}			
		}
	}

	public boolean isReady() {
		// If this pin activation is not streaming, then return true if the total 
		// number of values already being offered by the pin plus those being 
		// offered by the sources of incoming edges is at least equal to the 
		// minimum multiplicity of the pin.
		// If this pin activation is streaming, then return true if the minimum 
		// multiplicity is zero or if there is at least one offered value.

		boolean ready = super.isReady();
		if (ready) {
			int minimum = ((Pin) this.node).multiplicityElement.lower;
			if (this.isStreaming()) {
				if (minimum > 0) {
					minimum = 1;
				}
			}
			ready = this.getTotalValueCount() >= minimum;
		}

		return ready;
	} // isReady
	
	public boolean isReadyForStreaming() {
		// Return true if this pin activation is ready assuming that it
		// corresponds to a streaming parameter. In this case, it is
		// ready if it has a lower multiplicity bound of zero, or if
		// there is at least one offered value.
		
		return super.isReady() & 
				(((Pin) this.node).multiplicityElement.lower == 0 | 
				 getTotalValueCount() >= 1);
	}
	
	public boolean isStreaming() {
		// Return true if this pin activation is for a pin that corresponds
		// to a streaming input parameter.
		
		return this.streamingParameterValue != null;
	}
	
	public boolean streamingIsTerminated() {
		boolean isTerminated = false;
		
		_beginIsolation();
		isTerminated = this.streamingParameterValue.isTerminated();
		Debug.println("[streamingIsTerminated] isTerminated = " + isTerminated);
		_endIsolation();
		
		return isTerminated;
	}
	
	public int getTotalValueCount() {
		// Return the total number of values already being offered by the
		// pin plus those being offered by the sources of incoming edges.
		
		return this.countUnofferedTokens() + this.countOfferedValues();
	}
	
} // InputPinActivation
