
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

package fuml.syntax.classification;

import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.commonstructure.NamedElementList;
import fuml.syntax.commonstructure.VisibilityKind;

public abstract class Classifier extends fuml.syntax.commonstructure.Type {

	public boolean isAbstract = false;
	public fuml.syntax.classification.GeneralizationList generalization = new fuml.syntax.classification.GeneralizationList();
	public fuml.syntax.classification.FeatureList feature = new fuml.syntax.classification.FeatureList();
	public fuml.syntax.commonstructure.NamedElementList inheritedMember = new fuml.syntax.commonstructure.NamedElementList();
	public fuml.syntax.classification.PropertyList attribute = new fuml.syntax.classification.PropertyList();
	public fuml.syntax.classification.ClassifierList general = new fuml.syntax.classification.ClassifierList();
	public boolean isFinalSpecialization = false;

	protected void addFeature(fuml.syntax.classification.Feature feature) {
		// Note: This operation should not be used directly to add Properties.
		// The addAttribute operation should be used instead.

		this.feature.addValue(feature);
		feature._addFeaturingClassifier(this);
	} // addFeature

	protected void addAttribute(fuml.syntax.classification.Property attribute) {
		this.addFeature(attribute);
		this.attribute.addValue(attribute);
	} // addAttribute

	public void addGeneralization(
			fuml.syntax.classification.Generalization generalization) {
		this.addOwnedElement(generalization);
		this.generalization.addValue(generalization);
		generalization._setSpecific(this);
		this.general.addValue(generalization.general);

		NamedElementList inheritedMembers = this.inherit(generalization.general
				.inheritableMembers(this));

		for (int i = 0; i < inheritedMembers.size(); i++) {
			NamedElement inheritedMember = inheritedMembers.getValue(i);
			this.addMember(inheritedMember);
			this.inheritedMember.addValue(inheritedMember);
		}
	} // addGeneralization

	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	} // setIsAbstract

	public fuml.syntax.commonstructure.NamedElementList inherit(
			fuml.syntax.commonstructure.NamedElementList inhs) {
		NamedElementList inheritedElements = new NamedElementList();

		for (int i = 0; i < inhs.size(); i++) {
			inheritedElements.addValue(inhs.getValue(i));
		}

		return inheritedElements;

	} // inherit

	public fuml.syntax.commonstructure.NamedElementList inheritableMembers(
			fuml.syntax.classification.Classifier c) {
		NamedElementList inheritable = new NamedElementList();

		for (int i = 0; i < this.member.size(); i++) {
			NamedElement m = this.member.getValue(i);
			if (c.hasVisibilityOf(m)) {
				inheritable.addValue(m);
			}
		}

		return inheritable;
	} // inheritableMembers

	public boolean hasVisibilityOf(fuml.syntax.commonstructure.NamedElement n) {
		for (int i = 0; i < this.inheritedMember.size(); i++) {
			if (this.inheritedMember.getValue(i) == n) {
				return n.visibility != VisibilityKind.private_;
			}
		}

		return true;
	} // hasVisibilityOf

	public void setIsFinalSpecialization(boolean isFinalSpecialization) {
		this.isFinalSpecialization = isFinalSpecialization;
	} // setIsFinalSpecialization

} // Classifier
