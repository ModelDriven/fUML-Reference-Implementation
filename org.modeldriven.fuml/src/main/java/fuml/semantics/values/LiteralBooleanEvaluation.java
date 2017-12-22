
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

package fuml.semantics.values;

import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.syntax.values.LiteralBoolean;

public class LiteralBooleanEvaluation extends
		fuml.semantics.values.LiteralEvaluation {

	public fuml.semantics.classification.Value evaluate() {
		// Evaluate a literal boolean, producing a boolean value.

		LiteralBoolean literal = (LiteralBoolean) specification;
		BooleanValue booleanValue = new BooleanValue();
		booleanValue.type = this.getType("Boolean");
		booleanValue.value = literal.value;

		return booleanValue;

	} // evaluate

} // LiteralBooleanEvaluation
