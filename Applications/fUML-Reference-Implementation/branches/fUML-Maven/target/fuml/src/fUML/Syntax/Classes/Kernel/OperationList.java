
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

package fUML.Syntax.Classes.Kernel;

import java.util.ArrayList;

public class OperationList extends
		ArrayList<fUML.Syntax.Classes.Kernel.Operation> {
	public OperationList() {
		super();
	}

	public Operation getValue(int i) {
		return (Operation) get(i);
	}

	public void addValue(Operation v) {
		add(v);
	}

	public void addValue(int i, Operation v) {
		add(i, v);
	}

	public void setValue(int i, Operation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // OperationList
