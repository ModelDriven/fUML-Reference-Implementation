
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

public class SlotList extends ArrayList<fUML.Syntax.Classes.Kernel.Slot> {
	public SlotList() {
		super();
	}

	public Slot getValue(int i) {
		return (Slot) get(i);
	}

	public void addValue(Slot v) {
		add(v);
	}

	public void addValue(int i, Slot v) {
		add(i, v);
	}

	public void setValue(int i, Slot v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // SlotList
