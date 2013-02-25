
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

public class FeatureList extends ArrayList<fUML.Syntax.Classes.Kernel.Feature> {
	public FeatureList() {
		super();
	}

	public Feature getValue(int i) {
		return (Feature) get(i);
	}

	public void addValue(Feature v) {
		add(v);
	}

	public void addValue(int i, Feature v) {
		add(i, v);
	}

	public void setValue(int i, Feature v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // FeatureList
