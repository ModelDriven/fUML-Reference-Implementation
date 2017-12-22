
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

import fuml.semantics.simpleclassifiers.StringValue;
import fuml.syntax.values.LiteralString;

public class LiteralStringEvaluation extends
		fuml.semantics.values.LiteralEvaluation {

	public fuml.semantics.classification.Value evaluate() {
		// Evaluate a literal string, producing a string value.

		LiteralString literal = (LiteralString) specification;
		StringValue stringValue = new StringValue();
		stringValue.type = this.getType("String");
		stringValue.value = literal.value;

		return stringValue;
	} // evaluate

} // LiteralStringEvaluation
