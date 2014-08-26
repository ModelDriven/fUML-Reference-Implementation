
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

public abstract class GetNextEventStrategy extends
		fUML.Semantics.Loci.LociL1.SemanticStrategy {

	public String getName() {
		// Get next event strategies are always named "getNextEvent".

		return "getNextEvent";
	} // getName

	public abstract fUML.Semantics.CommonBehaviors.Communications.SignalInstance getNextEvent(
			fUML.Semantics.CommonBehaviors.Communications.ObjectActivation objectActivation);

} // GetNextEventStrategy
