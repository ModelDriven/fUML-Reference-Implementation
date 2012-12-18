
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

package fUML.Syntax.Activities.IntermediateActivities;

import java.util.ArrayList;

public class ActivityEdgeList extends
		ArrayList<fUML.Syntax.Activities.IntermediateActivities.ActivityEdge> {
	public ActivityEdgeList() {
		super();
	}

	public ActivityEdge getValue(int i) {
		return (ActivityEdge) get(i);
	}

	public void addValue(ActivityEdge v) {
		add(v);
	}

	public void addValue(int i, ActivityEdge v) {
		add(i, v);
	}

	public void setValue(int i, ActivityEdge v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ActivityEdgeList
