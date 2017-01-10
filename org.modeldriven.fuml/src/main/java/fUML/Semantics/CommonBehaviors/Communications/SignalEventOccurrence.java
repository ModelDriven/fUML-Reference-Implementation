/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Semantics.Classes.Kernel.FeatureValue;
import fUML.Semantics.Classes.Kernel.FeatureValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.ClassifierList;
import fUML.Syntax.CommonBehaviors.Communications.SignalEvent;

public class SignalEventOccurrence extends EventOccurrence {
	
	public SignalInstance signalInstance;

	@Override
	public boolean match(fUML.Syntax.CommonBehaviors.Communications.Trigger trigger) {
		// Match a trigger if it references a signal event whose signal is the type of the 
		// signal instance or one of its supertypes.
		
		boolean matches = false;
		if(trigger.event instanceof SignalEvent){
			SignalEvent event = (SignalEvent) trigger.event;
			if(event.signal == this.signalInstance.type) {
				matches = true;
			} else {
				matches = this.checkAllParents(this.signalInstance.type, event.signal);
			}
		}
		return matches;
	}
	
	public boolean checkAllParents(fUML.Syntax.Classes.Kernel.Classifier type,
			fUML.Syntax.Classes.Kernel.Classifier classifier) {
		// Check if the given classifier matches any of the direct or indirect
		// ancestors of a given type.

		ClassifierList directParents = type.general;
		boolean matched = false;
		int i = 1;
		while (!matched & i <= directParents.size()) {
			Classifier directParent = directParents.getValue(i - 1);
			if (directParent == classifier) {
				matched = true;
			} else {
				matched = this.checkAllParents(directParent, classifier);
			}
			i = i + 1;
		}

		return matched;
	}

	@Override
	public fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList getParameterValues() {
		// Return parameter values for feature of the signal instance, in order.
		// These are intended to be treated as if they are the values of effective
		// parameters of direction "in".
		
		ParameterValueList parameterValues = new ParameterValueList();
		FeatureValueList memberValues = this.signalInstance.getMemberValues();
		for(int i = 0; i < memberValues.size(); i++){
			FeatureValue feature = memberValues.getValue(i);
			ParameterValue parameterValue = new ParameterValue();
			parameterValue.values = feature.values;
			parameterValues.add(parameterValue);
		}
		return parameterValues;
	}

}
