
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

package fUML.Semantics.CommonBehaviors.BasicBehaviors;

import java.util.ArrayList;

public class OpaqueBehaviorExecutionList
		extends
		ArrayList<fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution> {
	public OpaqueBehaviorExecutionList() {
		super();
	}

	public OpaqueBehaviorExecution getValue(int i) {
		return (OpaqueBehaviorExecution) get(i);
	}

	public void addValue(OpaqueBehaviorExecution v) {
		add(v);
	}

	public void addValue(int i, OpaqueBehaviorExecution v) {
		add(i, v);
	}

	public void setValue(int i, OpaqueBehaviorExecution v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // OpaqueBehaviorExecutionList
