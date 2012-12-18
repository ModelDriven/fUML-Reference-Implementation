
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

package fUML.Syntax.Actions.IntermediateActions;

import java.util.ArrayList;

public class LinkEndCreationDataList extends
		ArrayList<fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData> {
	public LinkEndCreationDataList() {
		super();
	}

	public LinkEndCreationData getValue(int i) {
		return (LinkEndCreationData) get(i);
	}

	public void addValue(LinkEndCreationData v) {
		add(v);
	}

	public void addValue(int i, LinkEndCreationData v) {
		add(i, v);
	}

	public void setValue(int i, LinkEndCreationData v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // LinkEndCreationDataList
