
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

package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Actions.IntermediateActions.*;
import fUML.Semantics.Loci.*;

public class ReduceActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution currentExecution = null;

	public void doAction() {
		// Get the values of the collection input pin.
		// If the input pin has no values, then do nothing. Otherwise, do the
		// following.
		// Repeatedly invoke the reducer behavior on successive pairs to reduce
		// the collection to a single value, and place that value on the result
		// pin.
		// To invoke the reducer behavior, compile it to create an execution,
		// make the execution the current execution, place the appropriate
		// values on its input parameters, and execute it.

		ReduceAction action = (ReduceAction) (this.node);

		ValueList values = this.takeTokens(action.collection);

		if (values.size() > 0) {
			ParameterList parameters = action.reducer.ownedParameter;
			Parameter input1 = null;
			Parameter input2 = null;
			Parameter output = null;

			int i = 1;
			while (i <= parameters.size()) {
				Parameter parameter = parameters.getValue(i - 1);
				if (parameter.direction == ParameterDirectionKind.in) {
					if (input1 == null) {
						input1 = parameter;
					} else {
						input2 = parameter;
					}
				} else if (parameter.direction == ParameterDirectionKind.out
						| parameter.direction == ParameterDirectionKind.return_) {
					output = parameter;
				}
				i = i + 1;
			}

			ParameterValue parameterValue1 = new ParameterValue();
			parameterValue1.parameter = input1;
			parameterValue1.values = new ValueList();
			parameterValue1.values.addValue(values.getValue(0));

			int j = 2;
			while (j <= values.size()) {
				this.currentExecution = this.getExecutionLocus().factory
						.createExecution(action.reducer, this
								.getExecutionContext());

				this.currentExecution.setParameterValue(parameterValue1);

				ParameterValue parameterValue2 = new ParameterValue();
				parameterValue2.parameter = input2;
				parameterValue2.values = new ValueList();
				parameterValue2.values.addValue(values.getValue(j - 1));
				this.currentExecution.setParameterValue(parameterValue2);

				this.currentExecution.execute();

				parameterValue1.values = this.currentExecution
						.getParameterValue(output).values;

				j = j + 1;

				if (parameterValue1.values.isEmpty() & j <= values.size()) {
					parameterValue1.values.add(values.getValue(j - 1));
					j = j + 1;
				}

			}

			this.putTokens(action.result, parameterValue1.values);
		}
	} // doAction

	public void terminate() {
		// If there is a current execution, terminate it. Then terminate self.

		if (this.currentExecution != null) {
			this.currentExecution.terminate();
		}

		super.terminate();
	} // terminate

} // ReduceActionActivation
