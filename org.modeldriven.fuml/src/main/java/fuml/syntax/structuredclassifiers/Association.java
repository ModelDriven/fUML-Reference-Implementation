
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

package fuml.syntax.structuredclassifiers;

import fuml.syntax.classification.Property;

public class Association extends fuml.syntax.classification.Classifier {

	public boolean isDerived = false;
	public fuml.syntax.classification.PropertyList ownedEnd = new fuml.syntax.classification.PropertyList();
	public fuml.syntax.commonstructure.TypeList endType = new fuml.syntax.commonstructure.TypeList();
	public fuml.syntax.classification.PropertyList memberEnd = new fuml.syntax.classification.PropertyList();
	public fuml.syntax.classification.PropertyList navigableOwnedEnd = new fuml.syntax.classification.PropertyList();

	public void addOwnedEnd(fuml.syntax.classification.Property ownedEnd) {
		super.addFeature(ownedEnd);
		super.addOwnedMember(ownedEnd);

		this.ownedEnd.addValue(ownedEnd);
		ownedEnd._setOwningAssociation(this);

		this._addMemberEnd(ownedEnd);
	} // addOwnedEnd

	public void addNavigableOwnedEnd(
			fuml.syntax.classification.Property navigableOwnedEnd) {
		// Note: A navigable end must also be set as an owned end using
		// setOwnedEnd.

		this.navigableOwnedEnd.addValue(navigableOwnedEnd);
	} // addNavigableOwnedEnd
	
	public void addMemberEnd(fuml.syntax.classification.Property memberEnd) {
		// Note: This operation should not be used for owned ends. The
		// operation addOwnedEnd should be used instead.

		this.addMember(memberEnd);
		this._addMemberEnd(memberEnd);
	}
	
	protected void _addMemberEnd(fuml.syntax.classification.Property memberEnd) {
		this.memberEnd.addValue(memberEnd);
		memberEnd._setAssociation(this);

		if (memberEnd.typedElement.type != null) {
			this.endType.addValue(memberEnd.typedElement.type);
		}

		if (this.memberEnd.size() == 2) {
			Property opposite = this.memberEnd.get(0);
			memberEnd._setOpposite(opposite);
			opposite._setOpposite(memberEnd);
		} else if (this.memberEnd.size() > 2) {
			for (Property end : this.memberEnd) {
				end._setOpposite(null);
			}
		}
		
	}

} // Association
