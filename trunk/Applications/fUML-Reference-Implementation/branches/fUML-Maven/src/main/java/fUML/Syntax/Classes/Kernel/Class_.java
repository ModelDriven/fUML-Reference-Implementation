
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

public class Class_ extends
		fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier {

	public fUML.Syntax.Classes.Kernel.OperationList ownedOperation = new fUML.Syntax.Classes.Kernel.OperationList();
	public boolean isActive = false;
	public fUML.Syntax.CommonBehaviors.Communications.ReceptionList ownedReception = new fUML.Syntax.CommonBehaviors.Communications.ReceptionList();
	public fUML.Syntax.Classes.Kernel.PropertyList ownedAttribute = new fUML.Syntax.Classes.Kernel.PropertyList();
	public fUML.Syntax.Classes.Kernel.ClassifierList nestedClassifier = new fUML.Syntax.Classes.Kernel.ClassifierList();
	public boolean isID = false;
	public fUML.Syntax.Classes.Kernel.Class_List superClass = new fUML.Syntax.Classes.Kernel.Class_List();

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	} // setIsActive

	public void addGeneralization(
			fUML.Syntax.Classes.Kernel.Generalization generalization) {
		super.addGeneralization(generalization);

		if (generalization.general instanceof Class_) {
			this.superClass.addValue((Class_) generalization.general);
		}
	} // addGeneralization

	public void addOwnedAttribute(
			fUML.Syntax.Classes.Kernel.Property ownedAttribute) {
		super.addAttribute(ownedAttribute);
		super.addOwnedMember(ownedAttribute);

		this.ownedAttribute.addValue(ownedAttribute);
		ownedAttribute._setClass(this);
	} // addOwnedAttribute

	public void addOwnedOperation(
			fUML.Syntax.Classes.Kernel.Operation ownedOperation) {
		super.addFeature(ownedOperation);
		super.addOwnedMember(ownedOperation);

		this.ownedOperation.addValue(ownedOperation);
		ownedOperation._setClass(this);
	} // addOwnedOperation

	public void addOwnedReception(
			fUML.Syntax.CommonBehaviors.Communications.Reception ownedReception) {
		super.addOwnedMember(ownedReception);
		super.addFeature(ownedReception);

		this.ownedReception.addValue(ownedReception);
	} // addOwnedReception

	public fUML.Syntax.Classes.Kernel.NamedElementList inherit(
			fUML.Syntax.Classes.Kernel.NamedElementList inhs) {
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
			fUML.Syntax.Classes.Kernel.Classifier nestedClassifier) {
		this.addOwnedMember(nestedClassifier);
		this.nestedClassifier.addValue(nestedClassifier);
	} // addNestedClassifier

} // Class
