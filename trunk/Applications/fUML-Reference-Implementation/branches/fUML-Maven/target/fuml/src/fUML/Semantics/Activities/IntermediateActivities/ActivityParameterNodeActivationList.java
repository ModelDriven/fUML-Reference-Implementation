
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

package fUML.Semantics.Activities.IntermediateActivities;

import java.util.ArrayList;

public class ActivityParameterNodeActivationList
		extends
		ArrayList<fUML.Semantics.Activities.IntermediateActivities.ActivityParameterNodeActivation> {
	public ActivityParameterNodeActivationList() {
		super();
	}

	public ActivityParameterNodeActivation getValue(int i) {
		return (ActivityParameterNodeActivation) get(i);
	}

	public void addValue(ActivityParameterNodeActivation v) {
		add(v);
	}

	public void addValue(int i, ActivityParameterNodeActivation v) {
		add(i, v);
	}

	public void setValue(int i, ActivityParameterNodeActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ActivityParameterNodeActivationList
