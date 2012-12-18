
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

package fUML.Semantics.Activities.ExtraStructuredActivities;

import java.util.ArrayList;

public class ExpansionActivationGroupList
		extends
		ArrayList<fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionActivationGroup> {
	public ExpansionActivationGroupList() {
		super();
	}

	public ExpansionActivationGroup getValue(int i) {
		return (ExpansionActivationGroup) get(i);
	}

	public void addValue(ExpansionActivationGroup v) {
		add(v);
	}

	public void addValue(int i, ExpansionActivationGroup v) {
		add(i, v);
	}

	public void setValue(int i, ExpansionActivationGroup v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ExpansionActivationGroupList
