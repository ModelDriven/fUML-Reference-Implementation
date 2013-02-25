
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

public class ExecutableNodeList
		extends
		ArrayList<fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode> {
	public ExecutableNodeList() {
		super();
	}

	public ExecutableNode getValue(int i) {
		return (ExecutableNode) get(i);
	}

	public void addValue(ExecutableNode v) {
		add(v);
	}

	public void addValue(int i, ExecutableNode v) {
		add(i, v);
	}

	public void setValue(int i, ExecutableNode v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ExecutableNodeList
