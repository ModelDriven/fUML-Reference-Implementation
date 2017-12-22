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

package fuml.semantics.loci;

import fuml.semantics.commonbehavior.CallEventBehavior;
import fuml.semantics.commonbehavior.CallEventExecution;
import fuml.semantics.values.InstanceValueEvaluation;
import fuml.semantics.values.LiteralBooleanEvaluation;
import fuml.semantics.values.LiteralIntegerEvaluation;
import fuml.semantics.values.LiteralNullEvaluation;
import fuml.semantics.values.LiteralRealEvaluation;
import fuml.semantics.values.LiteralStringEvaluation;
import fuml.semantics.values.LiteralUnlimitedNaturalEvaluation;
import fuml.syntax.classification.InstanceValue;
import fuml.syntax.values.LiteralBoolean;
import fuml.syntax.values.LiteralInteger;
import fuml.syntax.values.LiteralNull;
import fuml.syntax.values.LiteralReal;
import fuml.syntax.values.LiteralString;
import fuml.syntax.values.LiteralUnlimitedNatural;

public class ExecutionFactoryL1 extends
		fuml.semantics.loci.ExecutionFactory {

	public fuml.semantics.loci.SemanticVisitor instantiateVisitor(
			fuml.syntax.commonstructure.Element element) {
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
