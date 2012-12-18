
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

public class Association extends fUML.Syntax.Classes.Kernel.Classifier {

	public boolean isDerived = false;
	public fUML.Syntax.Classes.Kernel.PropertyList ownedEnd = new fUML.Syntax.Classes.Kernel.PropertyList();
	public fUML.Syntax.Classes.Kernel.TypeList endType = new fUML.Syntax.Classes.Kernel.TypeList();
	public fUML.Syntax.Classes.Kernel.PropertyList memberEnd = new fUML.Syntax.Classes.Kernel.PropertyList();
	public fUML.Syntax.Classes.Kernel.PropertyList navigableOwnedEnd = new fUML.Syntax.Classes.Kernel.PropertyList();

	public void addOwnedEnd(fUML.Syntax.Classes.Kernel.Property ownedEnd) {
		super.addFeature(ownedEnd);
		super.addOwnedMember(ownedEnd);

		this.ownedEnd.addValue(ownedEnd);
		ownedEnd._setOwningAssociation(this);

		this.memberEnd.addValue(ownedEnd);
		ownedEnd._setAssociation(this);

		if (ownedEnd.typedElement.type != null) {
			this.endType.addValue(ownedEnd.typedElement.type);
		}

		if (this.memberEnd.size() == 2) {
			Property opposite = this.memberEnd.get(0);
			ownedEnd._setOpposite(opposite);
			opposite._setOpposite(ownedEnd);
		} else if (this.memberEnd.size() > 2) {
			for (Property memberEnd : this.memberEnd) {
				memberEnd._setOpposite(null);
			}
		}
	} // addOwnedEnd

	public void addNavigableOwnedEnd(
			fUML.Syntax.Classes.Kernel.Property navigableOwnedEnd) {
		// Note: A navigable end must also be set as an owned end using
		// setOwnedEnd.

		this.navigableOwnedEnd.addValue(navigableOwnedEnd);
	} // addNavigableOwnedEnd

} // Association
