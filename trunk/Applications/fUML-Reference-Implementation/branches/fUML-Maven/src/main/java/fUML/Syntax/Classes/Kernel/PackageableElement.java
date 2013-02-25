
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

public abstract class PackageableElement extends
		fUML.Syntax.Classes.Kernel.NamedElement {

	public fUML.Syntax.Classes.Kernel.VisibilityKind visibility = fUML.Syntax.Classes.Kernel.VisibilityKind.public_;

	public void setVisibility(
			fUML.Syntax.Classes.Kernel.VisibilityKind visibility) {
		super.setVisibility(visibility);
		this.visibility = visibility;
	} // setVisibility

} // PackageableElement
