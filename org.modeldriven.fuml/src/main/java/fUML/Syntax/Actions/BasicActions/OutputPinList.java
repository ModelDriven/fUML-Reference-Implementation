
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

public class OutputPinList extends
		ArrayList<fUML.Syntax.Actions.BasicActions.OutputPin> {
	public OutputPinList() {
		super();
	}

	public OutputPin getValue(int i) {
		return (OutputPin) get(i);
	}

	public void addValue(OutputPin v) {
		add(v);
	}

	public void addValue(int i, OutputPin v) {
		add(i, v);
	}

	public void setValue(int i, OutputPin v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // OutputPinList
