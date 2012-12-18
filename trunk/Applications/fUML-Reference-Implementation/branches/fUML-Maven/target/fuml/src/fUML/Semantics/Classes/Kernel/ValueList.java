
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

package fUML.Semantics.Classes.Kernel;

import java.util.ArrayList;

public class ValueList extends ArrayList<fUML.Semantics.Classes.Kernel.Value> {
	public ValueList() {
		super();
	}

	public Value getValue(int i) {
		return (Value) get(i);
	}

	public void addValue(Value v) {
		add(v);
	}

	public void addValue(int i, Value v) {
		add(i, v);
	}

	public void setValue(int i, Value v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ValueList
