
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

public class Slot extends fUML.Syntax.Classes.Kernel.Element {

	public fUML.Syntax.Classes.Kernel.InstanceSpecification owningInstance = null;
	public fUML.Syntax.Classes.Kernel.StructuralFeature definingFeature = null;
	public fUML.Syntax.Classes.Kernel.ValueSpecificationList value = new fUML.Syntax.Classes.Kernel.ValueSpecificationList();

	public void setDefiningFeature(
			fUML.Syntax.Classes.Kernel.StructuralFeature definingFeature) {
		this.definingFeature = definingFeature;
	} // setDefiningFeature

	public void addValue(fUML.Syntax.Classes.Kernel.ValueSpecification value) {
		this.addOwnedElement(value);
		this.value.addValue(value);
	} // addValue

	public void _setOwningInstance(
			fUML.Syntax.Classes.Kernel.InstanceSpecification owningInstance) {
		this.owningInstance = owningInstance;
	} // _setOwningInstance

} // Slot
