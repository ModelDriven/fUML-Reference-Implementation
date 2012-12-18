
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

public abstract class OpaqueBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution {

	public void execute() {
		// Execute the body of the opaque behavior.

		Debug.println("[execute] Opaque behavior " + this.getBehavior().name
				+ "...");

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
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters);

} // OpaqueBehaviorExecution
