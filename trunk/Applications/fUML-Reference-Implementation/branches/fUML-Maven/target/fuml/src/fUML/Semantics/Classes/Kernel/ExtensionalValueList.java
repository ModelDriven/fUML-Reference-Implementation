
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

public class ExtensionalValueList extends
		ArrayList<fUML.Semantics.Classes.Kernel.ExtensionalValue> {
	public ExtensionalValueList() {
		super();
	}

	public ExtensionalValue getValue(int i) {
		return (ExtensionalValue) get(i);
	}

	public void addValue(ExtensionalValue v) {
		add(v);
	}

	public void addValue(int i, ExtensionalValue v) {
		add(i, v);
	}

	public void setValue(int i, ExtensionalValue v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ExtensionalValueList
