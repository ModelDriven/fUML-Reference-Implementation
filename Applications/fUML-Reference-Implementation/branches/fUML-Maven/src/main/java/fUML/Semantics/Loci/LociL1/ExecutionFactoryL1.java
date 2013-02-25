
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

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;

public class ExecutionFactoryL1 extends
		fUML.Semantics.Loci.LociL1.ExecutionFactory {

	public fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
			fUML.Syntax.Classes.Kernel.Element element) {
		// Instantiate a visitor object for the given element (at Conformance
		// Level 1)

		SemanticVisitor visitor = null;

		if (element instanceof LiteralBoolean) {
			visitor = new LiteralBooleanEvaluation();
		}

		else if (element instanceof fUML.Syntax.Classes.Kernel.LiteralString) {
			visitor = new LiteralStringEvaluation();
		}

		else if (element instanceof fUML.Syntax.Classes.Kernel.LiteralNull) {
			visitor = new LiteralNullEvaluation();
		}

		else if (element instanceof fUML.Syntax.Classes.Kernel.InstanceValue) {
			visitor = new InstanceValueEvaluation();
		}

		else if (element instanceof fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural) {
			visitor = new LiteralUnlimitedNaturalEvaluation();
		}

		else if (element instanceof fUML.Syntax.Classes.Kernel.LiteralInteger) {
			visitor = new LiteralIntegerEvaluation();
		}

		else if (element instanceof LiteralReal) {
			visitor = new LiteralRealEvaluation();
		}

		return visitor;
	} // instantiateVisitor

} // ExecutionFactoryL1
