
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

package fuml.syntax.packages;

import fuml.syntax.commonstructure.ElementImport;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.commonstructure.NamedElementList;
import fuml.syntax.commonstructure.PackageImport;
import fuml.syntax.commonstructure.PackageableElement;
import fuml.syntax.commonstructure.PackageableElementList;
import fuml.syntax.commonstructure.Type;
import fuml.syntax.commonstructure.VisibilityKind;

public class Package extends fuml.syntax.commonstructure.Namespace {

	public fuml.syntax.commonstructure.PackageableElementList packagedElement = new fuml.syntax.commonstructure.PackageableElementList();
	public fuml.syntax.commonstructure.TypeList ownedType = new fuml.syntax.commonstructure.TypeList();
	public fuml.syntax.packages.PackageList nestedPackage = new fuml.syntax.packages.PackageList();
	public fuml.syntax.packages.Package nestingPackage = null;
	public String URI = "";

	public void addPackagedElement(
			fuml.syntax.commonstructure.PackageableElement packagedElement) {
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

	public fuml.syntax.commonstructure.PackageableElementList visibleMembers() {
		PackageableElementList visibleMembers = new PackageableElementList();

		for (int i = 0; i < this.packagedElement.size(); i++) {
			PackageableElement member = this.packagedElement.getValue(i);
			if (this.makesVisible(member)) {
				visibleMembers.addValue(member);
			}
		}

		return visibleMembers;
	} // visibleMembers

	public boolean makesVisible(fuml.syntax.commonstructure.NamedElement el) {
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
