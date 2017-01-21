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

package fUML.Semantics.Loci.LociL1;

import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.Communications.CallEventBehavior;
import fUML.Semantics.CommonBehaviors.Communications.CallEventExecution;

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

		else if (element instanceof LiteralString) {
			visitor = new LiteralStringEvaluation();
		}

		else if (element instanceof LiteralNull) {
			visitor = new LiteralNullEvaluation();
		}

		else if (element instanceof InstanceValue) {
			visitor = new InstanceValueEvaluation();
		}

		else if (element instanceof LiteralUnlimitedNatural) {
			visitor = new LiteralUnlimitedNaturalEvaluation();
		}

		else if (element instanceof LiteralInteger) {
			visitor = new LiteralIntegerEvaluation();
		}

		else if (element instanceof LiteralReal) {
			visitor = new LiteralRealEvaluation();
		}
		
		else if (element instanceof CallEventBehavior) {
			visitor = new CallEventExecution();
		}

		return visitor;
	} // instantiateVisitor

} // ExecutionFactoryL1
