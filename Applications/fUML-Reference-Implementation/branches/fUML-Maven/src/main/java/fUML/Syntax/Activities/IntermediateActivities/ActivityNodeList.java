
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

public class ActivityNodeList extends
		ArrayList<fUML.Syntax.Activities.IntermediateActivities.ActivityNode> {
	public ActivityNodeList() {
		super();
	}

	public ActivityNode getValue(int i) {
		return (ActivityNode) get(i);
	}

	public void addValue(ActivityNode v) {
		add(v);
	}

	public void addValue(int i, ActivityNode v) {
		add(i, v);
	}

	public void setValue(int i, ActivityNode v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ActivityNodeList
