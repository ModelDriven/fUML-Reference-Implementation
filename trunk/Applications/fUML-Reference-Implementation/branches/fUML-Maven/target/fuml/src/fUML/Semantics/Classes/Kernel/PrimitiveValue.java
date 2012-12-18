
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

public abstract class PrimitiveValue extends
		fUML.Semantics.Classes.Kernel.Value {

	public fUML.Syntax.Classes.Kernel.PrimitiveType type = null;

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new value that is equal to this primitive value.

		PrimitiveValue newValue = (PrimitiveValue) (super.copy());

		newValue.type = this.type;
		return newValue;
	} // copy

	public fUML.Syntax.Classes.Kernel.ClassifierList getTypes() {
		// Return the single primitive type of this value.

		ClassifierList types = new ClassifierList();
		types.addValue(this.type);
		return types;
	} // getTypes

} // PrimitiveValue
