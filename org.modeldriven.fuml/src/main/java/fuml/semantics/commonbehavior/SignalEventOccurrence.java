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
import fuml.syntax.commonbehavior.Event;
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
	public fuml.semantics.commonbehavior.ParameterValueList getParameterValues(Event event) {
		// Return parameter values for the features of the signal instance, in order,
	    // corresponding to the attributes of the declared signal of the given event.
		// These are intended to be treated as if they are the values of effective
		// parameters of direction "in".
		// (Note that the given event must be a signal event, and the signal instance 
		// of this signal event occurrence must be a direct or indirect instance of 
		// the event signal.)

		ParameterValueList parameterValues = new ParameterValueList();		
		if (event instanceof SignalEvent) {
			FeatureValueList memberValues = 
					this.signalInstance.getMemberValues(((SignalEvent)event).signal);
			for(int i = 0; i < memberValues.size(); i++){
				FeatureValue feature = memberValues.getValue(i);
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.values = feature.values;
				parameterValues.add(parameterValue);
			}
		}
		return parameterValues;
	}

}
