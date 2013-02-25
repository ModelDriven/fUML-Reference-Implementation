
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

public class GeneralizationList extends
		ArrayList<fUML.Syntax.Classes.Kernel.Generalization> {
	public GeneralizationList() {
		super();
	}

	public Generalization getValue(int i) {
		return (Generalization) get(i);
	}

	public void addValue(Generalization v) {
		add(v);
	}

	public void addValue(int i, Generalization v) {
		add(i, v);
	}

	public void setValue(int i, Generalization v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // GeneralizationList
