
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

package fuml.semantics.activities;

public class JoinNodeActivation extends
		fuml.semantics.activities.ControlNodeActivation {

	public boolean isReady() {
		// Check that all incoming edges have sources that are offering tokens.

		boolean ready = true;
		int i = 1;
		while (ready & i <= this.incomingEdges.size()) {
			ready = this.incomingEdges.getValue(i - 1).hasOffer();
			i = i + 1;
		}

		return ready;
	} // isReady

} // JoinNodeActivation
