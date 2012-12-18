
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

public abstract class LiteralEvaluation extends
		fUML.Semantics.Classes.Kernel.Evaluation {

	public fUML.Syntax.Classes.Kernel.PrimitiveType getType(
			String builtInTypeName) {
		// Get the type of the specification. If that is null, then use the
		// built-in type of the given name.

		PrimitiveType type = (PrimitiveType) (this.specification.type);

		if (type == null) {
			type = this.locus.factory.getBuiltInType(builtInTypeName);
		}

		return type;
	} // getType

} // LiteralEvaluation
