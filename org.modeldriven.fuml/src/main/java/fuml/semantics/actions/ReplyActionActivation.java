package fuml.semantics.actions;

import fuml.Debug;
import fuml.semantics.classification.ValueList;
import fuml.semantics.commonbehavior.ParameterValue;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.InputPinList;
import fuml.syntax.actions.ReplyAction;
import fuml.syntax.commonbehavior.CallEvent;
import fuml.syntax.commonbehavior.Trigger;

public class ReplyActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		// Reply to the call represented by the return information on
		// the return information pin using the reply values given
		// on the reply value pins.
		
		ReplyAction action = (ReplyAction) this.node;
		Trigger replyToCall = action.replyToCall;
		InputPinList replyValuePins = action.replyValue;
		InputPin returnInformationPin = action.returnInformation;
		
		ValueList values = this.takeTokens(returnInformationPin);
		ReturnInformation returnInformation = (ReturnInformation) values.getValue(0);
		Debug.println("[doAction] action = " + action.name + " returnInformation = " + returnInformation);

		if (replyToCall.event instanceof CallEvent & 
				((CallEvent)replyToCall.event).operation == 
				returnInformation.getOperation()) {
		
			ParameterValueList parameterValues = new ParameterValueList();
			int i = 1;
			while (i <= replyValuePins.size()) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.values = this.takeTokens(replyValuePins.getValue(i - 1));
				parameterValues.addValue(parameterValue);
				i = i + 1;
			}

			returnInformation.reply(parameterValues);
		}
	}

}
