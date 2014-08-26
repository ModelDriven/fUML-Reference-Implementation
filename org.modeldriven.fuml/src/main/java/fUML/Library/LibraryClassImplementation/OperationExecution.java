
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

package fUML.Library.LibraryClassImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public class OperationExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution {

	public void set(
			fUML.Library.LibraryClassImplementation.ImplementationObject context,
			fUML.Syntax.Classes.Kernel.Operation operation) {
		this.context = context;

		OpaqueBehavior method = new OpaqueBehavior();
		method.specification = operation;

		for (int i = 0; i < operation.ownedParameter.size(); i++) {
			Parameter operationParameter = operation.ownedParameter.getValue(i);
			Parameter methodParameter = new Parameter();
			methodParameter.name = operationParameter.name;
			methodParameter.direction = operationParameter.direction;
			methodParameter.type = operationParameter.type;
			methodParameter.multiplicityElement = operationParameter.multiplicityElement;
			method.ownedParameter.addValue(methodParameter);
		}

		this.types.addValue(method);
	} // set

	public fUML.Semantics.Classes.Kernel.Value new_() {
		return (Value) (new OperationExecution());
	} // new_

	public void execute() {
		((ImplementationObject) (this.context)).execute(this);
	} // execute

	public String getOperationName() {
		return this.getBehavior().specification.name;
	} // getOperationName

	public fUML.Syntax.Classes.Kernel.Parameter getParameter(
			String parameterName) {
		// MexSystem.println("[getParameter] parameterName = " + parameterName);

		Behavior method = this.getBehavior();

		for (int i = 0; i < method.ownedParameter.size(); i++) {
			Parameter parameter = method.ownedParameter.getValue(i);
			if (parameter.name.equals(parameterName)) {
				// MexSystem.println("[getParameter] Parameter found.");
				return parameter;
			}
		}

		// MexSystem.println("[getParameter] Parameter not found.");
		return null;
	} // getParameter

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue getParameterValue(
			String parameterName) {
		return this.getParameterValue(this.getParameter(parameterName));
	} // getParameterValue

	public void setParameterValue(String parameterName,
			fUML.Semantics.Classes.Kernel.ValueList values) {
		ParameterValue parameterValue = new ParameterValue();
		parameterValue.parameter = this.getParameter(parameterName);
		parameterValue.values = values;

		this.setParameterValue(parameterValue);
	} // setParameterValue

	public void setParameterValue(String parameterName,
			fUML.Semantics.Classes.Kernel.Value value) {
		ValueList valueList = new ValueList();
		valueList.addValue(value);
		this.setParameterValue(parameterName, valueList);
	} // setParameterValue

} // OperationExecution
