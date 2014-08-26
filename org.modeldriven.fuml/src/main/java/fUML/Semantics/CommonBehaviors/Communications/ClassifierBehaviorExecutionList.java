
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

package fUML.Semantics.CommonBehaviors.Communications;

import java.util.ArrayList;

public class ClassifierBehaviorExecutionList
		extends
		ArrayList<fUML.Semantics.CommonBehaviors.Communications.ClassifierBehaviorExecution> {
	public ClassifierBehaviorExecutionList() {
		super();
	}

	public ClassifierBehaviorExecution getValue(int i) {
		return (ClassifierBehaviorExecution) get(i);
	}

	public void addValue(ClassifierBehaviorExecution v) {
		add(v);
	}

	public void addValue(int i, ClassifierBehaviorExecution v) {
		add(i, v);
	}

	public void setValue(int i, ClassifierBehaviorExecution v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ClassifierBehaviorExecutionList
