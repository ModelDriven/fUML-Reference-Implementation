
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

public abstract class GetNextEventStrategy extends
		fuml.semantics.loci.SemanticStrategy {

	public String getName() {
		// Get next event strategies are always named "getNextEvent".

		return "getNextEvent";
	} // getName

	public abstract fuml.semantics.commonbehavior.EventOccurrence getNextEvent(
			fuml.semantics.commonbehavior.ObjectActivation objectActivation);

} // GetNextEventStrategy
