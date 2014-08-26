
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

public abstract class Evaluation extends
		fUML.Semantics.Loci.LociL1.SemanticVisitor {

	public fUML.Syntax.Classes.Kernel.ValueSpecification specification = null;
	public fUML.Semantics.Loci.LociL1.Locus locus = null;

	public abstract fUML.Semantics.Classes.Kernel.Value evaluate();

} // Evaluation
