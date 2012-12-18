
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

public class ValuesList
		extends
		ArrayList<fUML.Semantics.Activities.CompleteStructuredActivities.Values> {
	public ValuesList() {
		super();
	}

	public Values getValue(int i) {
		return (Values) get(i);
	}

	public void addValue(Values v) {
		add(v);
	}

	public void addValue(int i, Values v) {
		add(i, v);
	}

	public void setValue(int i, Values v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ValuesList
