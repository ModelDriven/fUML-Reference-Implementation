
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

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class FIFOGetNextEventStrategy extends
		fUML.Semantics.CommonBehaviors.Communications.GetNextEventStrategy {

	public fUML.Semantics.CommonBehaviors.Communications.SignalInstance getNextEvent(
			fUML.Semantics.CommonBehaviors.Communications.ObjectActivation objectActivation) {
		// Get the first event from the given event pool. The event is removed
		// from the pool.

		SignalInstance signalInstance = objectActivation.eventPool.getValue(0);
		objectActivation.eventPool.removeValue(0);
		return signalInstance;
	} // getNextEvent

} // FIFOGetNextEventStrategy
