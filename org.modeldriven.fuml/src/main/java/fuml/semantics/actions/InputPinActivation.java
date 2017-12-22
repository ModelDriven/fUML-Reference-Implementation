
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

import fuml.syntax.actions.Pin;

public class InputPinActivation extends
		fuml.semantics.actions.PinActivation {

	public void receiveOffer() {
		// Forward the offer to the action activation. [When all input pins are
		// ready, the action will fire them.]

		this.actionActivation.receiveOffer();
	} // receiveOffer

	public boolean isReady() {
		// Return true if the total number of values already being offered by
		// this pin plus those being offered
		// by the sources of incoming edges is at least equal to the minimum
		// multiplicity of the pin.

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
