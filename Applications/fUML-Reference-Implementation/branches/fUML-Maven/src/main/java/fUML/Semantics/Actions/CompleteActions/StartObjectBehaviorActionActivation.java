
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

public class StartObjectBehaviorActionActivation extends
		fUML.Semantics.Actions.BasicActions.InvocationActionActivation {

	public void doAction() {
		// Get the value on the object input pin. If it is not a reference, then
		// do nothing.
		// Start the behavior of the referent object for the classifier given as
		// the type of the object input pin, with parameter values taken from
		// the argument input pins.
		// If the object input pin has no type, then start the classifier
		// behaviors of all types of the referent object.

		StartObjectBehaviorAction action = (StartObjectBehaviorAction) (this.node);

		Value object = this.takeTokens(action.object).getValue(0);

		if (object instanceof Reference) {
			Class_ type = (Class_) (action.object.typedElement.type);
			InputPinList argumentPins = action.argument;

			ParameterValueList inputs = new ParameterValueList();

			if (type != null) {
				Behavior behavior;

				if (type instanceof Behavior) {
					behavior = (Behavior) type;
				} else {
					behavior = type.classifierBehavior;
				}

				if (behavior != null) {
					ParameterList parameters = behavior.ownedParameter;

					int pinNumber = 1;
					int i = 1;
					while (i <= parameters.size()) {
						Parameter parameter = parameters.getValue(i - 1);
						int j = pinNumber;
						if (parameter.direction == ParameterDirectionKind.in
								| parameter.direction == ParameterDirectionKind.inout) {
							ParameterValue parameterValue = new ParameterValue();
							parameterValue.parameter = parameter;
							parameterValue.values = this
									.takeTokens(argumentPins.getValue(j - 1));
							inputs.addValue(parameterValue);
							j = j + 1;
						}
						pinNumber = j;
						i = i + 1;
					}
				}
			}

			((Reference) object).startBehavior(type, inputs);
		}
	} // doAction

} // StartObjectBehaviorActionActivation
