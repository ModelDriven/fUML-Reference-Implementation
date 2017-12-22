
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

import fuml.semantics.simpleclassifiers.RealValue;
import fuml.syntax.values.LiteralReal;

public class LiteralRealEvaluation extends
		fuml.semantics.values.LiteralEvaluation {

	public fuml.semantics.classification.Value evaluate() {
		// Evaluate a real integer, producing a real value.

		LiteralReal literal = (LiteralReal) specification;
		RealValue realValue = new RealValue();
		realValue.type = this.getType("Real");
		realValue.value = literal.value;
		return realValue;
	} // evaluate

} // LiteralRealEvaluation
