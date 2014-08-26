
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

public class FeatureValueList extends
		ArrayList<fUML.Semantics.Classes.Kernel.FeatureValue> {
	public FeatureValueList() {
		super();
	}

	public FeatureValue getValue(int i) {
		return (FeatureValue) get(i);
	}

	public void addValue(FeatureValue v) {
		add(v);
	}

	public void addValue(int i, FeatureValue v) {
		add(i, v);
	}

	public void setValue(int i, FeatureValue v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // FeatureValueList
