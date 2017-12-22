
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

public class Slot extends fuml.syntax.commonstructure.Element {

	public fuml.syntax.classification.InstanceSpecification owningInstance = null;
	public fuml.syntax.classification.StructuralFeature definingFeature = null;
	public fuml.syntax.values.ValueSpecificationList value = new fuml.syntax.values.ValueSpecificationList();

	public void setDefiningFeature(
			fuml.syntax.classification.StructuralFeature definingFeature) {
		this.definingFeature = definingFeature;
	} // setDefiningFeature

	public void addValue(fuml.syntax.values.ValueSpecification value) {
		this.addOwnedElement(value);
		this.value.addValue(value);
	} // addValue

	public void _setOwningInstance(
			fuml.syntax.classification.InstanceSpecification owningInstance) {
		this.owningInstance = owningInstance;
	} // _setOwningInstance

} // Slot
