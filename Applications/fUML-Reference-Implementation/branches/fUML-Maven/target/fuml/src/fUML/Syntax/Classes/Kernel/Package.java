
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

public class Package extends fUML.Syntax.Classes.Kernel.Namespace {

	public fUML.Syntax.Classes.Kernel.PackageableElementList packagedElement = new fUML.Syntax.Classes.Kernel.PackageableElementList();
	public fUML.Syntax.Classes.Kernel.TypeList ownedType = new fUML.Syntax.Classes.Kernel.TypeList();
	public fUML.Syntax.Classes.Kernel.PackageList nestedPackage = new fUML.Syntax.Classes.Kernel.PackageList();
	public fUML.Syntax.Classes.Kernel.Package nestingPackage = null;
	public String URI = "";

	public void addPackagedElement(
			fUML.Syntax.Classes.Kernel.PackageableElement packagedElement) {
		super.addOwnedMember(packagedElement);
		this.packagedElement.addValue(packagedElement);

		if (packagedElement instanceof Type) {
			this.ownedType.addValue((Type) packagedElement);
			((Type) packagedElement)._setPackage(this);
		}

		if (packagedElement instanceof Package) {
			this.nestedPackage.addValue((Package) packagedElement);
			((Package) packagedElement).nestingPackage = this;
		}

	} // addPackagedElement

	public fUML.Syntax.Classes.Kernel.PackageableElementList visibleMembers() {
		PackageableElementList visibleMembers = new PackageableElementList();

		for (int i = 0; i < this.packagedElement.size(); i++) {
			PackageableElement member = this.packagedElement.getValue(i);
			if (this.makesVisible(member)) {
				visibleMembers.addValue(member);
			}
		}

		return visibleMembers;
	} // visibleMembers

	public boolean makesVisible(fUML.Syntax.Classes.Kernel.NamedElement el) {
		for (int i = 0; i < this.ownedMember.size(); i++) {
			NamedElement member = this.ownedMember.getValue(i);
			if (member == el) {
				return member.visibility == null
						|| member.visibility == VisibilityKind.public_;
			}
		}

		for (int i = 0; i < this.elementImport.size(); i++) {
			ElementImport elementImport = this.elementImport.getValue(i);
			if (elementImport.importedElement == el) {
				return true;
			}
		}

		for (int i = 0; i < this.packageImport.size(); i++) {
			PackageImport packageImport = this.packageImport.getValue(i);
			if (packageImport.visibility == VisibilityKind.public_) {
				// NOTE: This won't work unless the imported package members
				// have already been loaded
				NamedElementList packageMembers = packageImport.importedPackage.member;
				for (int j = 0; j < packageMembers.size(); j++) {
					if (packageMembers.getValue(j) == el) {
						return true;
					}
				}
			}
		}

		return false;
	} // makesVisible

	public void setURI(String URI) {
		this.URI = URI;
	} // setURI

} // Package
