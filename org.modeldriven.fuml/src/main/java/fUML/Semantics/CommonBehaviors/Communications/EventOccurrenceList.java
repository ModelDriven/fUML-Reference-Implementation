
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import java.util.ArrayList;

public class EventOccurrenceList extends
		ArrayList<fUML.Semantics.CommonBehaviors.Communications.EventOccurrence> {
	public EventOccurrenceList() {
		super();
	}

	public EventOccurrence getValue(int i) {
		return (EventOccurrence) get(i);
	}

	public void addValue(EventOccurrence v) {
		add(v);
	}

	public void addValue(int i, EventOccurrence v) {
		add(i, v);
	}

	public void setValue(int i, EventOccurrence v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // SignalInstanceList
