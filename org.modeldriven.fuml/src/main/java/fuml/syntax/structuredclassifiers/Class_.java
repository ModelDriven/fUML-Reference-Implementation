
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

import fuml.syntax.classification.RedefinableElement;
import fuml.syntax.classification.RedefinableElementList;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.commonstructure.NamedElementList;

public class Class_ extends
		fuml.syntax.commonbehavior.BehavioredClassifier {

	public fuml.syntax.classification.OperationList ownedOperation = new fuml.syntax.classification.OperationList();
	public boolean isActive = false;
	public fuml.syntax.simpleclassifiers.ReceptionList ownedReception = new fuml.syntax.simpleclassifiers.ReceptionList();
	public fuml.syntax.classification.PropertyList ownedAttribute = new fuml.syntax.classification.PropertyList();
	public fuml.syntax.classification.ClassifierList nestedClassifier = new fuml.syntax.classification.ClassifierList();
	public boolean isID = false;
	public fuml.syntax.structuredclassifiers.Class_List superClass = new fuml.syntax.structuredclassifiers.Class_List();

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	} // setIsActive

	public void addGeneralization(
			fuml.syntax.classification.Generalization generalization) {
		super.addGeneralization(generalization);

		if (generalization.general instanceof Class_) {
			this.superClass.addValue((Class_) generalization.general);
		}
	} // addGeneralization

	public void addOwnedAttribute(
			fuml.syntax.classification.Property ownedAttribute) {
		super.addAttribute(ownedAttribute);
		super.addOwnedMember(ownedAttribute);

		this.ownedAttribute.addValue(ownedAttribute);
		ownedAttribute._setClass(this);
	} // addOwnedAttribute

	public void addOwnedOperation(
			fuml.syntax.classification.Operation ownedOperation) {
		super.addFeature(ownedOperation);
		super.addOwnedMember(ownedOperation);

		this.ownedOperation.addValue(ownedOperation);
		ownedOperation._setClass(this);
	} // addOwnedOperation

	public void addOwnedReception(
			fuml.syntax.simpleclassifiers.Reception ownedReception) {
		super.addOwnedMember(ownedReception);
		super.addFeature(ownedReception);

		this.ownedReception.addValue(ownedReception);
	} // addOwnedReception

	public fuml.syntax.commonstructure.NamedElementList inherit(
			fuml.syntax.commonstructure.NamedElementList inhs) {
		// "The inherit operation is overridden to exclude redefined properties."

		RedefinableElementList redefinableMembers = new RedefinableElementList();

		for (int i = 0; i < this.ownedMember.size(); i++) {
			if (this.ownedMember.getValue(i) instanceof RedefinableElement) {
				redefinableMembers
						.addValue((RedefinableElement) this.ownedMember
								.getValue(i));
			}
		}

		NamedElementList inherited = new NamedElementList();

		for (int i = 0; i < inhs.size(); i++) {
			NamedElement inh = inhs.getValue(i);
			boolean exclude = false;
			for (int j = 0; j < redefinableMembers.size(); j++) {
				RedefinableElementList redefinedElements = redefinableMembers
						.getValue(j).redefinedElement;
				for (int k = 0; k < redefinedElements.size(); k++) {
					if (redefinedElements.getValue(k) == inh) {
						exclude = true;
						break;
					}
				}
				if (exclude)
					break;
			}

			if (!exclude) {
				inherited.addValue(inh);
			}
		}

		return inherited;
	} // inherit

	public void addNestedClassifier(
			fuml.syntax.classification.Classifier nestedClassifier) {
		this.addOwnedMember(nestedClassifier);
		this.nestedClassifier.addValue(nestedClassifier);
	} // addNestedClassifier

} // Class
