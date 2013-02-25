
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

package fUML.Semantics.CommonBehaviors.BasicBehaviors;

import java.util.ArrayList;

public class ParameterValueList extends
		ArrayList<fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue> {
	public ParameterValueList() {
		super();
	}

	public ParameterValue getValue(int i) {
		return (ParameterValue) get(i);
	}

	public void addValue(ParameterValue v) {
		add(v);
	}

	public void addValue(int i, ParameterValue v) {
		add(i, v);
	}

	public void setValue(int i, ParameterValue v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ParameterValueList
