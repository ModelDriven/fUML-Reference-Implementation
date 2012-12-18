
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

package fUML.Semantics.Activities.CompleteStructuredActivities;

import java.util.ArrayList;

public class ClauseActivationList
		extends
		ArrayList<fUML.Semantics.Activities.CompleteStructuredActivities.ClauseActivation> {
	public ClauseActivationList() {
		super();
	}

	public ClauseActivation getValue(int i) {
		return (ClauseActivation) get(i);
	}

	public void addValue(ClauseActivation v) {
		add(v);
	}

	public void addValue(int i, ClauseActivation v) {
		add(i, v);
	}

	public void setValue(int i, ClauseActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ClauseActivationList
