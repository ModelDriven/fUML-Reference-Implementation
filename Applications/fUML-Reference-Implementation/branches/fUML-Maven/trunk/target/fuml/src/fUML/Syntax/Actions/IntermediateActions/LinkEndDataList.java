
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

public class LinkEndDataList extends
		ArrayList<fUML.Syntax.Actions.IntermediateActions.LinkEndData> {
	public LinkEndDataList() {
		super();
	}

	public LinkEndData getValue(int i) {
		return (LinkEndData) get(i);
	}

	public void addValue(LinkEndData v) {
		add(v);
	}

	public void addValue(int i, LinkEndData v) {
		add(i, v);
	}

	public void setValue(int i, LinkEndData v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // LinkEndDataList
