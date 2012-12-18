
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

package fUML.Syntax.CommonBehaviors.Communications;

import java.util.ArrayList;

public class ReceptionList extends
		ArrayList<fUML.Syntax.CommonBehaviors.Communications.Reception> {
	public ReceptionList() {
		super();
	}

	public Reception getValue(int i) {
		return (Reception) get(i);
	}

	public void addValue(Reception v) {
		add(v);
	}

	public void addValue(int i, Reception v) {
		add(i, v);
	}

	public void setValue(int i, Reception v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // ReceptionList
