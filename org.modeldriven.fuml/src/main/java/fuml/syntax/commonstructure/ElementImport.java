
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

package fuml.syntax.commonstructure;

public class ElementImport extends fuml.syntax.commonstructure.Element {

	public fuml.syntax.commonstructure.VisibilityKind visibility = null;
	public String alias = "";
	public fuml.syntax.commonstructure.PackageableElement importedElement = null;
	public fuml.syntax.commonstructure.Namespace importingNamespace = null;

	public void setAlias(String alias) {
		this.alias = alias;
	} // setAlias

	public void setVisibility(
			fuml.syntax.commonstructure.VisibilityKind visibility) {
		this.visibility = visibility;
	} // setVisibility

	public void setImportedElement(
			fuml.syntax.commonstructure.PackageableElement importedElement) {
		this.importedElement = importedElement;
	} // setImportedElement

	public void _setImportingNamespace(
			fuml.syntax.commonstructure.Namespace importingNamespace) {
		this.importingNamespace = importingNamespace;
	} // _setImportingNamespace

} // ElementImport
