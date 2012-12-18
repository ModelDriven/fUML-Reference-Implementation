
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

package fUML.Syntax.Activities.CompleteStructuredActivities;

import java.util.ArrayList;

public class ClauseList extends
		ArrayList<fUML.Syntax.Activities.CompleteStructuredActivities.Clause> {
	public ClauseList() {
		super();
	}

	public Clause getValue(int i) {
		return (Clause) get(i);
	}

	public void addValue(Clause v) {
		add(v);
	}

	public void addValue(int i, Clause v) {
		add(i, v);
	}

	public void setValue(int i, Clause v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ClauseList
