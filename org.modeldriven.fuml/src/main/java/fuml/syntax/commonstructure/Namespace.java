/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package fuml.syntax.commonstructure;

public abstract class Namespace extends
		fuml.syntax.commonstructure.PackageableElement {

	public fuml.syntax.commonstructure.NamedElementList member = new fuml.syntax.commonstructure.NamedElementList();
	public fuml.syntax.commonstructure.NamedElementList ownedMember = new fuml.syntax.commonstructure.NamedElementList();
	public fuml.syntax.commonstructure.ElementImportList elementImport = new fuml.syntax.commonstructure.ElementImportList();
	public fuml.syntax.commonstructure.PackageImportList packageImport = new fuml.syntax.commonstructure.PackageImportList();
	public fuml.syntax.commonstructure.PackageableElementList importedMember = new fuml.syntax.commonstructure.PackageableElementList();

	protected void addOwnedMember(
			fuml.syntax.commonstructure.NamedElement ownedMember) {
		this.addOwnedElement(ownedMember);

		this.ownedMember.addValue(ownedMember);
		ownedMember.namespace = this;

		this.addMember(ownedMember);
	} // addOwnedMember

	protected void addMember(fuml.syntax.commonstructure.NamedElement member) {
		// Note: This operation should not be used for owned members. The
		// operation addOwnedMember should be used instead.

		// Debug.println("[addMember] member is a " +
		// member.getClass().getName() + "; name = " + member.name);

		this.member.addValue(member);

	} // addMember

	public void addElementImport(
			fuml.syntax.commonstructure.ElementImport elementImport) {
		this.addOwnedElement(elementImport);

		this.elementImport.addValue(elementImport);
		elementImport.importingNamespace = this;

		this.addImportedMember(elementImport.importedElement);
	} // addElementImport

	public void addPackageImport(
			fuml.syntax.commonstructure.PackageImport packageImport) {
		this.addOwnedElement(packageImport);

		this.packageImport.addValue(packageImport);
		packageImport.importingNamespace = this;

		PackageableElementList importedElements = packageImport.importedPackage
				.visibleMembers();
		for (int i = 0; i < importedElements.size(); i++) {
			PackageableElement importedElement = importedElements.getValue(i);
			this.addImportedMember(importedElement);
		}
	} // addPackageImport

	private void addImportedMember(
			fuml.syntax.commonstructure.PackageableElement importedMember) {
		this.addMember(importedMember);
		this.importedMember.addValue(importedMember);
	} // addImportedMember

} // Namespace
