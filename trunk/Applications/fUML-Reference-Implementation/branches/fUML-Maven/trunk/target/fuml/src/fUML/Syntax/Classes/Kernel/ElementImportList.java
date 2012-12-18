
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

public class ElementImportList extends
		ArrayList<fUML.Syntax.Classes.Kernel.ElementImport> {
	public ElementImportList() {
		super();
	}

	public ElementImport getValue(int i) {
		return (ElementImport) get(i);
	}

	public void addValue(ElementImport v) {
		add(v);
	}

	public void addValue(int i, ElementImport v) {
		add(i, v);
	}

	public void setValue(int i, ElementImport v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ElementImportList
