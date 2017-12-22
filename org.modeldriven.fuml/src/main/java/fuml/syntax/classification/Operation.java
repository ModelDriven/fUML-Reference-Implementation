
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

import UMLPrimitiveTypes.*;

public class Operation extends fuml.syntax.classification.BehavioralFeature {

	public boolean isQuery = false;
	public boolean isOrdered = false;
	public boolean isUnique = true;
	public int lower = 0;
	public UnlimitedNatural upper = null;
	public fuml.syntax.structuredclassifiers.Class_ class_ = null;
	public fuml.syntax.classification.OperationList redefinedOperation = new fuml.syntax.classification.OperationList();
	public fuml.syntax.commonstructure.Type type = null;
	public fuml.syntax.classification.ParameterList ownedParameter = new fuml.syntax.classification.ParameterList();

	public void setIsQuery(boolean isQuery) {
		this.isQuery = isQuery;
	} // setIsQuery

	public void addOwnedParameter(
			fuml.syntax.classification.Parameter ownedParameter) {
		super.addOwnedParameter(ownedParameter);
		this.ownedParameter.addValue(ownedParameter);
		ownedParameter._setOperation(this);

		if (ownedParameter.direction == ParameterDirectionKind.return_) {
			this.isOrdered = ownedParameter.multiplicityElement.isOrdered;
			this.isUnique = ownedParameter.multiplicityElement.isUnique;
			this.lower = ownedParameter.multiplicityElement.lower;
			this.upper = ownedParameter.multiplicityElement.upper;
			this.type = ownedParameter.type;
		}
	} // addOwnedParameter

	public void addRedefinedOperation(
			fuml.syntax.classification.Operation redefinedOperation) {
		super.addRedefinedElement(redefinedOperation);
		this.redefinedOperation.addValue(redefinedOperation);
	} // addRedefinedOperation

	public void addMethod(
			fuml.syntax.commonbehavior.Behavior method) {
		// Note: To have a method, an operation must be owned by a class. The
		// method must be an owned behavior of the class.

		this.method.addValue(method);
		method._setSpecification(this);

	} // addMethod

	public void _setClass(fuml.syntax.structuredclassifiers.Class_ class_) {
		this.class_ = class_;
	} // _setClass

} // Operation
