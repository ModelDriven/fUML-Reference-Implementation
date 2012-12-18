
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

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class TypedElement extends fUML.Syntax.Classes.Kernel.NamedElement {

	public fUML.Syntax.Classes.Kernel.Type type = null;

	public void setType(fUML.Syntax.Classes.Kernel.Type type) {
		this.type = type;
	} // setType

} // TypedElement
