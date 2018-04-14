
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

public class LiteralNullEvaluation extends
		fuml.semantics.values.LiteralEvaluation {

	public fuml.semantics.values.Value evaluate() {
		// Evaluate a literal null, returning nothing (since a null represents
		// an "absence of any value").

		return null;
	} // evaluate

} // LiteralNullEvaluation
