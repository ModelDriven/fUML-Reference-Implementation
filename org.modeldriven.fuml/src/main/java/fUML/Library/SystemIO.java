
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

package fUML.Library;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class SystemIO extends fUML.Library.PrimitiveBehaviors {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior WriteLine = null;

	public SystemIO(fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		fUML.Syntax.Classes.Kernel.ParameterList parameters = new fUML.Syntax.Classes.Kernel.ParameterList();
		parameters.addValue(this.createInputParameter("value", null, 1, 1));
		this.WriteLine = this
				.createPrimitiveBehavior(
						"WriteLine",
						parameters,
						new fUML.Library.SystemIOImplementation.SystemWriteLineBehaviorExecution(),
						factory);
	} // SystemIO

} // SystemIO
