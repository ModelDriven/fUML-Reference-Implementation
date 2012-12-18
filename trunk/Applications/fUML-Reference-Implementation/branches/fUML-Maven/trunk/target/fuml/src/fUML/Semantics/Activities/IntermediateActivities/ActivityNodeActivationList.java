
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

public class ActivityNodeActivationList
		extends
		ArrayList<fUML.Semantics.Activities.IntermediateActivities.ActivityNodeActivation> {
	public ActivityNodeActivationList() {
		super();
	}

	public ActivityNodeActivation getValue(int i) {
		return (ActivityNodeActivation) get(i);
	}

	public void addValue(ActivityNodeActivation v) {
		add(v);
	}

	public void addValue(int i, ActivityNodeActivation v) {
		add(i, v);
	}

	public void setValue(int i, ActivityNodeActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ActivityNodeActivationList
