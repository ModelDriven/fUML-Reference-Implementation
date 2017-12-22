
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

public class Property extends fuml.syntax.classification.StructuralFeature {

	public boolean isDerived = false;
	public boolean isReadOnly = false;
	public boolean isDerivedUnion = false;
	public fuml.syntax.classification.AggregationKind aggregation = fuml.syntax.classification.AggregationKind.none;
	public boolean isComposite = false;
	public fuml.syntax.structuredclassifiers.Association owningAssociation = null;
	public fuml.syntax.simpleclassifiers.DataType datatype = null;
	public fuml.syntax.structuredclassifiers.Association association = null;
	public fuml.syntax.structuredclassifiers.Class_ class_ = null;
	public fuml.syntax.classification.Property opposite = null;
	public fuml.syntax.values.ValueSpecification defaultValue = null;
	public boolean isID = false;

	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	} // setIsReadOnly

	public void setAggregation(
			fuml.syntax.classification.AggregationKind aggregation) {
		this.aggregation = aggregation;
		this.isComposite = (aggregation == AggregationKind.composite);
	} // setAggregation

	public void _setAssociation(
			fuml.syntax.structuredclassifiers.Association association) {
		this.association = association;
	} // _setAssociation

	public void _setClass(fuml.syntax.structuredclassifiers.Class_ class_) {
		this.class_ = class_;
	} // _setClass

	public void _setDatatype(fuml.syntax.simpleclassifiers.DataType datatype) {
		this.datatype = datatype;
	} // _setDataType

	public void setIsID(boolean isID) {
		this.isID = isID;
	} // setIsID

	public void _setOwningAssociation(
			fuml.syntax.structuredclassifiers.Association association) {
		this.association = association;
	} // _setOwningAssociation

	public void _setOpposite(fuml.syntax.classification.Property opposite) {
		this.opposite = opposite;
	} // _setOpposite

} // Property
