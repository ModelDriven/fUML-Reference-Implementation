
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

public class FIFOGetNextEventStrategy extends
		fuml.semantics.commonbehavior.GetNextEventStrategy {

	public fuml.semantics.commonbehavior.EventOccurrence getNextEvent(
			fuml.semantics.commonbehavior.ObjectActivation objectActivation) {
		// Get the first event from the given event pool. The event is removed
		// from the pool.

		EventOccurrence eventOccurrence = objectActivation.eventPool.getValue(0);
		objectActivation.eventPool.removeValue(0);
		return eventOccurrence;
	} // getNextEvent

} // FIFOGetNextEventStrategy
