
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

package fUML.Syntax.CommonBehaviors.BasicBehaviors;

import java.util.ArrayList;

public class BehaviorList extends
		ArrayList<fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior> {
	public BehaviorList() {
		super();
	}

	public Behavior getValue(int i) {
		return (Behavior) get(i);
	}

	public void addValue(Behavior v) {
		add(v);
	}

	public void addValue(int i, Behavior v) {
		add(i, v);
	}

	public void setValue(int i, Behavior v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // BehaviorList
