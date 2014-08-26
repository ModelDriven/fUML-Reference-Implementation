
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

package fUML.Syntax.Actions.BasicActions;

import java.util.ArrayList;

public class InputPinList extends
		ArrayList<fUML.Syntax.Actions.BasicActions.InputPin> {
	public InputPinList() {
		super();
	}

	public InputPin getValue(int i) {
		return (InputPin) get(i);
	}

	public void addValue(InputPin v) {
		add(v);
	}

	public void addValue(int i, InputPin v) {
		add(i, v);
	}

	public void setValue(int i, InputPin v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // InputPinList
