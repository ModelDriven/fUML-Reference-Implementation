
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

public class LiteralUnlimitedNaturalEvaluation extends
		fUML.Semantics.Classes.Kernel.LiteralEvaluation {

	public fUML.Semantics.Classes.Kernel.Value evaluate() {
		// Evaluate a literal unlimited natural producing an unlimited natural
		// value.

		LiteralUnlimitedNatural literal = (LiteralUnlimitedNatural) specification;
		UnlimitedNaturalValue unlimitedNaturalValue = new UnlimitedNaturalValue();
		unlimitedNaturalValue.type = this.getType("UnlimitedNatural");
		unlimitedNaturalValue.value = literal.value;

		return unlimitedNaturalValue;

	} // evaluate

} // LiteralUnlimitedNaturalEvaluation
