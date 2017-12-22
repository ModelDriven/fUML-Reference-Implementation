
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

import fuml.Debug;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;

public abstract class OpaqueBehaviorExecution extends
		fuml.semantics.commonbehavior.Execution {

	public void execute() {
		// Execute the body of the opaque behavior.

		Debug.println("[execute] Opaque behavior " + this.getBehavior().name + "...");

		ParameterList parameters = this.getBehavior().ownedParameter;

		ParameterValueList inputs = new ParameterValueList();
		ParameterValueList outputs = new ParameterValueList();

		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.getValue(i);

			if ((parameter.direction == ParameterDirectionKind.in)
					| (parameter.direction == ParameterDirectionKind.inout)) {
				inputs.addValue(this.getParameterValue(parameter));
			}

			if ((parameter.direction == ParameterDirectionKind.inout)
					| (parameter.direction == ParameterDirectionKind.out)
					| (parameter.direction == ParameterDirectionKind.return_)) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.parameter = parameter;
				this.setParameterValue(parameterValue);
				outputs.addValue(parameterValue);
			}
		}

		this.doBody(inputs, outputs);
	} // execute

	public abstract void doBody(
			fuml.semantics.commonbehavior.ParameterValueList inputParameters,
			fuml.semantics.commonbehavior.ParameterValueList outputParameters);

} // OpaqueBehaviorExecution
