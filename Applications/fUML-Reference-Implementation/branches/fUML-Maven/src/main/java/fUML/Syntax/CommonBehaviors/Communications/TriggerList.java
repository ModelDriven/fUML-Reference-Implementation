
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

package fUML.Syntax.CommonBehaviors.Communications;

import java.util.ArrayList;

public class TriggerList extends
		ArrayList<fUML.Syntax.CommonBehaviors.Communications.Trigger> {
	public TriggerList() {
		super();
	}

	public Trigger getValue(int i) {
		return (Trigger) get(i);
	}

	public void addValue(Trigger v) {
		add(v);
	}

	public void addValue(int i, Trigger v) {
		add(i, v);
	}

	public void setValue(int i, Trigger v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // TriggerList
