
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

public class ElementImport extends fUML.Syntax.Classes.Kernel.Element {

	public fUML.Syntax.Classes.Kernel.VisibilityKind visibility = null;
	public String alias = "";
	public fUML.Syntax.Classes.Kernel.PackageableElement importedElement = null;
	public fUML.Syntax.Classes.Kernel.Namespace importingNamespace = null;

	public void setAlias(String alias) {
		this.alias = alias;
	} // setAlias

	public void setVisibility(
			fUML.Syntax.Classes.Kernel.VisibilityKind visibility) {
		this.visibility = visibility;
	} // setVisibility

	public void setImportedElement(
			fUML.Syntax.Classes.Kernel.PackageableElement importedElement) {
		this.importedElement = importedElement;
	} // setImportedElement

	public void _setImportingNamespace(
			fUML.Syntax.Classes.Kernel.Namespace importingNamespace) {
		this.importingNamespace = importingNamespace;
	} // _setImportingNamespace

} // ElementImport
