
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

package fUML.Semantics.CommonBehaviors.Communications;

import java.util.ArrayList;

public class SignalInstanceList extends
		ArrayList<fUML.Semantics.CommonBehaviors.Communications.SignalInstance> {
	public SignalInstanceList() {
		super();
	}

	public SignalInstance getValue(int i) {
		return (SignalInstance) get(i);
	}

	public void addValue(SignalInstance v) {
		add(v);
	}

	public void addValue(int i, SignalInstance v) {
		add(i, v);
	}

	public void setValue(int i, SignalInstance v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // SignalInstanceList
