
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

public abstract class NamedElement extends fUML.Syntax.Classes.Kernel.Element {

	public String name = "";
	public fUML.Syntax.Classes.Kernel.VisibilityKind visibility = null;
	public String qualifiedName = "";
	public fUML.Syntax.Classes.Kernel.Namespace namespace = null;

	public void setName(String name) {
		this.name = name;

		if (name != null) {
			if (this.namespace == null) {
				this.qualifiedName = name;
			} else if (this.namespace.qualifiedName != null) {
				this.qualifiedName = this.namespace.qualifiedName + "::" + name;
			}
		}

	} // setName

	public void setVisibility(
			fUML.Syntax.Classes.Kernel.VisibilityKind visibility) {
		this.visibility = visibility;
	} // setVisibility

	public void _setNamespace(fUML.Syntax.Classes.Kernel.Namespace namespace) {
		this.namespace = namespace;

		if (this.name != null) {
			if (this.namespace == null) {
				this.qualifiedName = name;
			} else if (this.namespace.qualifiedName != null) {
				this.qualifiedName = this.namespace.qualifiedName + "::" + name;
			}
		}
	} // _setNamespace

} // NamedElement
