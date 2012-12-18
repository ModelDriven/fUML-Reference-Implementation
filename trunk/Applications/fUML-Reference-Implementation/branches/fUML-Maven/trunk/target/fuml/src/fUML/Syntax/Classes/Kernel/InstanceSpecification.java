
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

public class InstanceSpecification extends
		fUML.Syntax.Classes.Kernel.NamedElement {

	public fUML.Syntax.Classes.Kernel.ClassifierList classifier = new fUML.Syntax.Classes.Kernel.ClassifierList();
	public fUML.Syntax.Classes.Kernel.SlotList slot = new fUML.Syntax.Classes.Kernel.SlotList();

	public void addClassifier(fUML.Syntax.Classes.Kernel.Classifier classifier) {
		this.classifier.addValue(classifier);
	} // addClassifier

	public void addSlot(fUML.Syntax.Classes.Kernel.Slot slot) {
		this.addOwnedElement(slot);
		this.slot.addValue(slot);
		slot._setOwningInstance(this);
	} // addSlot

} // InstanceSpecification
