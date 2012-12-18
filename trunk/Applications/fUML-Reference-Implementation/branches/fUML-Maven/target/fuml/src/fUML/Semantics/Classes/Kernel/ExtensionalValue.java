
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

package fUML.Semantics.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.*;
import fUML.Semantics.Loci.*;

public abstract class ExtensionalValue extends
		fUML.Semantics.Classes.Kernel.CompoundValue {

	public fUML.Semantics.Loci.LociL1.Locus locus = null;

	public void destroy() {
		// Remove this value from its locus (if it has not already been
		// destroyed).

		if (this.locus != null) {
			this.locus.remove(this);
		}
	} // destroy

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new extensional value with the same feature values at the
		// same locus as this one.

		ExtensionalValue newValue = (ExtensionalValue) (super.copy());

		if (this.locus != null) {
			this.locus.add(newValue);
		}

		return newValue;
	} // copy

} // ExtensionalValue
