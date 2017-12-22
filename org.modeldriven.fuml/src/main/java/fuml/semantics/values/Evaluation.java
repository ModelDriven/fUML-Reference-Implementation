
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

public abstract class Evaluation extends
		fuml.semantics.loci.SemanticVisitor {

	public fuml.syntax.values.ValueSpecification specification = null;
	public fuml.semantics.loci.Locus locus = null;

	public abstract fuml.semantics.classification.Value evaluate();

} // Evaluation
