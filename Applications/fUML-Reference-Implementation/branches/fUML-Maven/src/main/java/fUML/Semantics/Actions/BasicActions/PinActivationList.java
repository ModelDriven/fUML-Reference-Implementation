
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

package fUML.Semantics.Actions.BasicActions;

import java.util.ArrayList;

public class PinActivationList extends
		ArrayList<fUML.Semantics.Actions.BasicActions.PinActivation> {
	public PinActivationList() {
		super();
	}

	public PinActivation getValue(int i) {
		return (PinActivation) get(i);
	}

	public void addValue(PinActivation v) {
		add(v);
	}

	public void addValue(int i, PinActivation v) {
		add(i, v);
	}

	public void setValue(int i, PinActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // PinActivationList
