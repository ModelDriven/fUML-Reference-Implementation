
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

public class PackageImportList extends
		ArrayList<fUML.Syntax.Classes.Kernel.PackageImport> {
	public PackageImportList() {
		super();
	}

	public PackageImport getValue(int i) {
		return (PackageImport) get(i);
	}

	public void addValue(PackageImport v) {
		add(v);
	}

	public void addValue(int i, PackageImport v) {
		add(i, v);
	}

	public void setValue(int i, PackageImport v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // PackageImportList
