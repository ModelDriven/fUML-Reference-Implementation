/*
 * Copyright 2015-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import org.modeldriven.fuml.FumlObject;

public abstract class EventOccurrence extends FumlObject {
	
	public abstract boolean match(fUML.Syntax.CommonBehaviors.Communications.Trigger trigger);

	public boolean matchAny(fUML.Syntax.CommonBehaviors.Communications.TriggerList triggers) {		
		// Check that at least one of the given triggers is matched by this 
		// event occurrence.
		
		boolean matches = false;
		int i = 1;
		while(!matches & i <= triggers.size()){
			if(this.match(triggers.get(i-1))){
				matches = true;
			}
			i = i + 1;
		}
		return matches;
		
	}
	
	public abstract fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList getParameterValues();

}
