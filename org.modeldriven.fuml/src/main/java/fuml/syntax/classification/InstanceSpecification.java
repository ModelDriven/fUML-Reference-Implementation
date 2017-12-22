
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

public class InstanceSpecification extends
		fuml.syntax.commonstructure.NamedElement {

	public fuml.syntax.classification.ClassifierList classifier = new fuml.syntax.classification.ClassifierList();
	public fuml.syntax.classification.SlotList slot = new fuml.syntax.classification.SlotList();

	public void addClassifier(fuml.syntax.classification.Classifier classifier) {
		this.classifier.addValue(classifier);
	} // addClassifier

	public void addSlot(fuml.syntax.classification.Slot slot) {
		this.addOwnedElement(slot);
		this.slot.addValue(slot);
		slot._setOwningInstance(this);
	} // addSlot

} // InstanceSpecification
