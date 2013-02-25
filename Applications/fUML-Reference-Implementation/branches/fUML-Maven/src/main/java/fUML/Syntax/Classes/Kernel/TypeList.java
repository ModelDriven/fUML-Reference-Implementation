
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

public class TypeList extends ArrayList<fUML.Syntax.Classes.Kernel.Type> {
	public TypeList() {
		super();
	}

	public Type getValue(int i) {
		return (Type) get(i);
	}

	public void addValue(Type v) {
		add(v);
	}

	public void addValue(int i, Type v) {
		add(i, v);
	}

	public void setValue(int i, Type v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // TypeList
