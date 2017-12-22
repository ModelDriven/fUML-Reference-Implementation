/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.simpleclassifiers.SignalInstance;
import fuml.syntax.commonbehavior.SignalEvent;

public class SignalEventOccurrence extends EventOccurrence {
	
	public SignalInstance signalInstance;

	@Override
	public boolean match(fuml.syntax.commonbehavior.Trigger trigger) {
		// Match a trigger if it references a signal event whose signal is the type of the 
		// signal instance or one of its supertypes.
		
		boolean matches = false;
		if(trigger.event instanceof SignalEvent){
			SignalEvent event = (SignalEvent) trigger.event;
			matches = this.signalInstance.isInstanceOf(event.signal);
		}
		return matches;
	}
	
	@Override
	public fuml.semantics.commonbehavior.ParameterValueList getParameterValues() {
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
