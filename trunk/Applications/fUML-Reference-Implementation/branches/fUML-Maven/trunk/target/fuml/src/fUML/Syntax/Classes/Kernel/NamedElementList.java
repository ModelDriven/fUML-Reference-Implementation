
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

public class NamedElementList extends
		ArrayList<fUML.Syntax.Classes.Kernel.NamedElement> {
	public NamedElementList() {
		super();
	}

	public NamedElement getValue(int i) {
		return (NamedElement) get(i);
	}

	public void addValue(NamedElement v) {
		add(v);
	}

	public void addValue(int i, NamedElement v) {
		add(i, v);
	}

	public void setValue(int i, NamedElement v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // NamedElementList
