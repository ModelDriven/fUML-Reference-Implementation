
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

package fUML.Syntax.Activities.ExtraStructuredActivities;

import java.util.ArrayList;

public class ExpansionNodeList
		extends
		ArrayList<fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode> {
	public ExpansionNodeList() {
		super();
	}

	public ExpansionNode getValue(int i) {
		return (ExpansionNode) get(i);
	}

	public void addValue(ExpansionNode v) {
		add(v);
	}

	public void addValue(int i, ExpansionNode v) {
		add(i, v);
	}

	public void setValue(int i, ExpansionNode v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ExpansionNodeList
