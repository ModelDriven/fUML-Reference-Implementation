
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

public class EventAccepterList extends
		ArrayList<fUML.Semantics.CommonBehaviors.Communications.EventAccepter> {
	public EventAccepterList() {
		super();
	}

	public EventAccepter getValue(int i) {
		return (EventAccepter) get(i);
	}

	public void addValue(EventAccepter v) {
		add(v);
	}

	public void addValue(int i, EventAccepter v) {
		add(i, v);
	}

	public void setValue(int i, EventAccepter v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // EventAccepterList
