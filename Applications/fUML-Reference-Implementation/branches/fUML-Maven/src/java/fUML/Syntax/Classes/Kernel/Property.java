
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

public class Property extends fUML.Syntax.Classes.Kernel.StructuralFeature {

	public boolean isDerived = false;
	public boolean isReadOnly = false;
	public boolean isDerivedUnion = false;
	public fUML.Syntax.Classes.Kernel.AggregationKind aggregation = fUML.Syntax.Classes.Kernel.AggregationKind.none;
	public boolean isComposite = false;
	public fUML.Syntax.Classes.Kernel.Association owningAssociation = null;
	public fUML.Syntax.Classes.Kernel.DataType datatype = null;
	public fUML.Syntax.Classes.Kernel.Association association = null;
	public fUML.Syntax.Classes.Kernel.Class_ class_ = null;
	public fUML.Syntax.Classes.Kernel.Property opposite = null;
	public fUML.Syntax.Classes.Kernel.ValueSpecification defaultValue = null;
	public boolean isID = false;

	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	} // setIsReadOnly

	public void setAggregation(
			fUML.Syntax.Classes.Kernel.AggregationKind aggregation) {
		this.aggregation = aggregation;
		this.isComposite = (aggregation == AggregationKind.composite);
	} // setAggregation

	public void _setAssociation(
			fUML.Syntax.Classes.Kernel.Association association) {
		this.association = association;
	} // _setAssociation

	public void _setClass(fUML.Syntax.Classes.Kernel.Class_ class_) {
		this.class_ = class_;
	} // _setClass

	public void _setDatatype(fUML.Syntax.Classes.Kernel.DataType datatype) {
		this.datatype = datatype;
	} // _setDataType

	public void setIsID(boolean isID) {
		this.isID = isID;
	} // setIsID

	public void _setOwningAssociation(
			fUML.Syntax.Classes.Kernel.Association association) {
		this.association = association;
	} // _setOwningAssociation

	public void _setOpposite(fUML.Syntax.Classes.Kernel.Property opposite) {
		this.opposite = opposite;
	} // _setOpposite

} // Property
