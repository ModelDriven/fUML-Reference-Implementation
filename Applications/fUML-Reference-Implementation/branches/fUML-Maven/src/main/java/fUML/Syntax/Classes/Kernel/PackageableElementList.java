
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

public class PackageableElementList extends
		ArrayList<fUML.Syntax.Classes.Kernel.PackageableElement> {
	public PackageableElementList() {
		super();
	}

	public PackageableElement getValue(int i) {
		return (PackageableElement) get(i);
	}

	public void addValue(PackageableElement v) {
		add(v);
	}

	public void addValue(int i, PackageableElement v) {
		add(i, v);
	}

	public void setValue(int i, PackageableElement v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // PackageableElementList
