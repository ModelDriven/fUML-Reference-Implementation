
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

package fUML.Semantics.CommonBehaviors.BasicBehaviors;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.Loci.*;

public abstract class Execution extends fUML.Semantics.Classes.Kernel.Object_ {

	public fUML.Semantics.Classes.Kernel.Object_ context = null;
	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList parameterValues = new fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList();

	public abstract void execute();

	public void terminate() {
		// Terminate an ongoing execution. By default, do nothing.

		return;
	} // terminate

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new execution that has the same behavior and parameterValues
		// as this execution.

		// Debug.println("[Copy] execution = " + this);

		Execution newValue = (Execution) (super.copy());

		newValue.context = this.context;

		ParameterValueList parameterValues = this.parameterValues;
		for (int i = 0; i < parameterValues.size(); i++) {
			ParameterValue parameterValue = parameterValues.getValue(i);
			newValue.parameterValues.addValue(parameterValue.copy());
		}

		// Debug.println("[Copy] Done.");

		return newValue;
	} // copy

	public abstract fUML.Semantics.Classes.Kernel.Value new_();

	public void setParameterValue(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue parameterValue) {
		// Set the given parameter value for this execution.
		// If a parameter value already existed for the parameter of the given
		// parameter value, then replace its value.

		// Debug.println("[setParameterValue] parameter = " +
		// parameterValue.parameter.name + " with " +
		// parameterValue.values.size() + " values");

		ParameterValue existingParameterValue = this
				.getParameterValue(parameterValue.parameter);

		if (existingParameterValue == null) {
			this.parameterValues.addValue(parameterValue);
		} else {
			existingParameterValue.values = parameterValue.values;
		}

	} // setParameterValue

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue getParameterValue(
			fUML.Syntax.Classes.Kernel.Parameter parameter) {
		// Get the parameter value of this execution corresponding to the given
		// parameter (if any).

		ParameterValue parameterValue = null;
		int i = 1;
		while (parameterValue == null & i <= this.parameterValues.size()) {
			if (this.parameterValues.getValue(i - 1).parameter == parameter) {
				parameterValue = this.parameterValues.getValue(i - 1);
			}
			i = i + 1;
		}

		return parameterValue;

	} // getParameterValue

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList getOutputParameterValues() {
		// Return the parameter values for output (in-out, out and return)
		// parameters.

		ParameterValueList outputs = new ParameterValueList();
		ParameterValueList parameterValues = this.parameterValues;
		for (int i = 0; i < parameterValues.size(); i++) {
			ParameterValue parameterValue = parameterValues.getValue(i);
			Parameter parameter = parameterValue.parameter;
			if ((parameter.direction == ParameterDirectionKind.inout)
					| (parameter.direction == ParameterDirectionKind.out)
					| (parameter.direction == ParameterDirectionKind.return_)) {
				outputs.addValue(parameterValue);
			}
		}

		return outputs;
	} // getOutputParameterValues

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior getBehavior() {
		// Get the behavior that is the type of this execution.

		return (Behavior) (this.getTypes().getValue(0));
	} // getBehavior

} // Execution
