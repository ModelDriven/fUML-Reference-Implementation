
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

package fuml.semantics.commonbehavior;

import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.commonbehavior.Behavior;

public abstract class Execution extends fuml.semantics.structuredclassifiers.Object_ {

	public fuml.semantics.structuredclassifiers.Object_ context = null;
	public fuml.semantics.commonbehavior.ParameterValueList parameterValues = new fuml.semantics.commonbehavior.ParameterValueList();

	public abstract void execute();

	public void terminate() {
		// Terminate an ongoing execution. By default, do nothing.

		return;
	} // terminate

	public fuml.semantics.values.Value copy() {
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

	public abstract fuml.semantics.values.Value new_();

	public void setParameterValue(
			fuml.semantics.commonbehavior.ParameterValue parameterValue) {
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

	public fuml.semantics.commonbehavior.ParameterValue getParameterValue(
			fuml.syntax.classification.Parameter parameter) {
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

	public fuml.semantics.commonbehavior.ParameterValueList getOutputParameterValues() {
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

	public fuml.syntax.commonbehavior.Behavior getBehavior() {
		// Get the behavior that is the type of this execution.

		return (Behavior) (this.getTypes().getValue(0));
	} // getBehavior
	
	public void destroy() {
		// Terminate the execution before destroying it.
		
		this.terminate();
		super.destroy();
	}

} // Execution
