
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

package fUML.Semantics.Loci.LociL1;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class ChoiceStrategy extends
		fUML.Semantics.Loci.LociL1.SemanticStrategy {

	public String getName() {
		// The name of a choice strategy is always "choice".

		return "choice";
	} // getName

	public abstract int choose(int size);

} // ChoiceStrategy
