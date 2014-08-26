
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

public class OutputPinActivationList extends
		ArrayList<fUML.Semantics.Actions.BasicActions.OutputPinActivation> {
	public OutputPinActivationList() {
		super();
	}

	public OutputPinActivation getValue(int i) {
		return (OutputPinActivation) get(i);
	}

	public void addValue(OutputPinActivation v) {
		add(v);
	}

	public void addValue(int i, OutputPinActivation v) {
		add(i, v);
	}

	public void setValue(int i, OutputPinActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // OutputPinActivationList
