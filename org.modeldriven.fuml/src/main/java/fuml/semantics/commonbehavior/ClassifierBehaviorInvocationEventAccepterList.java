
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import java.util.ArrayList;

public class ClassifierBehaviorInvocationEventAccepterList
		extends
		ArrayList<fuml.semantics.commonbehavior.ClassifierBehaviorInvocationEventAccepter> {
	public ClassifierBehaviorInvocationEventAccepterList() {
		super();
	}

	public ClassifierBehaviorInvocationEventAccepter getValue(int i) {
		return (ClassifierBehaviorInvocationEventAccepter) get(i);
	}

	public void addValue(ClassifierBehaviorInvocationEventAccepter v) {
		add(v);
	}

	public void addValue(int i, ClassifierBehaviorInvocationEventAccepter v) {
		add(i, v);
	}

	public void setValue(int i, ClassifierBehaviorInvocationEventAccepter v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ClassifierBehaviorExecutionList
