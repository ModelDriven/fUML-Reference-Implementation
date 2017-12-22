
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

public class AcceptEventActionEventAccepter extends
		fuml.semantics.commonbehavior.EventAccepter {

	public fuml.semantics.actions.AcceptEventActionActivation actionActivation = null;

	public void accept(
			fuml.semantics.commonbehavior.EventOccurrence eventOccurrence) {
		// Accept an event occurrence and forward it to the action activation.

		this.actionActivation.accept(eventOccurrence);
	} // accept

	public boolean match(
			fuml.semantics.commonbehavior.EventOccurrence eventOccurrence) {
		// Return true if the given event occurrence matches a trigger of the accept event 
		// action of the action activation.

		return this.actionActivation.match(eventOccurrence);
	} // match

} // AcceptEventActionEventAccepter
