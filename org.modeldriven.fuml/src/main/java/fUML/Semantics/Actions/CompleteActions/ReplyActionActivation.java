package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import fUML.Semantics.Actions.BasicActions.ActionActivation;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.InputPinList;
import fUML.Syntax.Actions.CompleteActions.ReplyAction;

public class ReplyActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		// Reply to the call represented by the return information on
		// the return information pin using the reply values given
		// on the reply value pins.
		
		ReplyAction action = (ReplyAction) this.node;
		InputPinList replyValuePins = action.replyValue;
		InputPin returnInformationPin = action.returnInformation;
		
		ParameterValueList parameterValues = new ParameterValueList();
		int i = 1;
		while (i <= replyValuePins.size()) {
			ParameterValue parameterValue = new ParameterValue();
			parameterValue.values = this.takeTokens(replyValuePins.getValue(i - 1));
			parameterValues.addValue(parameterValue);
			i = i + 1;
		}

		ValueList values = this.takeTokens(returnInformationPin);
		ReturnInformation returnInformation = (ReturnInformation) values.getValue(0);
		Debug.println("[doAction] action = " + action.name + " returnInformation = " + returnInformation);
		returnInformation.reply(parameterValues);
	}

}
