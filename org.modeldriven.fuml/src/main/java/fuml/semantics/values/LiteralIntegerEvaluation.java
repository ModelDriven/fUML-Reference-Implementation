
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

import fuml.syntax.simpleclassifiers.IntegerValue;
import fuml.syntax.values.LiteralInteger;

public class LiteralIntegerEvaluation extends
		fuml.semantics.values.LiteralEvaluation {

	public fuml.semantics.classification.Value evaluate() {
		// Evaluate a literal integer, producing an integer value.

		LiteralInteger literal = (LiteralInteger) specification;
		IntegerValue integerValue = new IntegerValue();
		integerValue.type = this.getType("Integer");
		integerValue.value = literal.value;

		return integerValue;

	} // evaluate

} // LiteralIntegerEvaluation
