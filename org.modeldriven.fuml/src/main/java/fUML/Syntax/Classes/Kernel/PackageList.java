
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

public class PackageList extends ArrayList<fUML.Syntax.Classes.Kernel.Package> {
	public PackageList() {
		super();
	}

	public Package getValue(int i) {
		return (Package) get(i);
	}

	public void addValue(Package v) {
		add(v);
	}

	public void addValue(int i, Package v) {
		add(i, v);
	}

	public void setValue(int i, Package v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // PackageList
