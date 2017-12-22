
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

public abstract class NamedElement extends fuml.syntax.commonstructure.Element {

	public String name = "";
	public fuml.syntax.commonstructure.VisibilityKind visibility = null;
	public String qualifiedName = "";
	public fuml.syntax.commonstructure.Namespace namespace = null;

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
			fuml.syntax.commonstructure.VisibilityKind visibility) {
		this.visibility = visibility;
	} // setVisibility

	public void _setNamespace(fuml.syntax.commonstructure.Namespace namespace) {
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
