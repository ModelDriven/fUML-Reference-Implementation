
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

public class PrimitiveTypes extends org.modeldriven.fuml.FumlObject {

	public fUML.Syntax.Classes.Kernel.PrimitiveType Boolean = null;
	public fUML.Syntax.Classes.Kernel.PrimitiveType String = null;
	public fUML.Syntax.Classes.Kernel.PrimitiveType Integer = null;
	public fUML.Syntax.Classes.Kernel.PrimitiveType UnlimitedNatural = null;

	public PrimitiveTypes(fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		this.Boolean = this.createBuiltInType("Boolean", factory);
		this.String = this.createBuiltInType("String", factory);
		this.Integer = this.createBuiltInType("Integer", factory);
		this.UnlimitedNatural = this.createBuiltInType("UnlimitedNatural",
				factory);
	} // PrimitiveTypes

	public fUML.Syntax.Classes.Kernel.PrimitiveType createBuiltInType(
			String name, fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
		fUML.Syntax.Classes.Kernel.PrimitiveType type = new fUML.Syntax.Classes.Kernel.PrimitiveType();
		type.name = name;
		factory.addBuiltInType(type);

		return type;
	} // createBuiltInType

} // PrimitiveTypes
