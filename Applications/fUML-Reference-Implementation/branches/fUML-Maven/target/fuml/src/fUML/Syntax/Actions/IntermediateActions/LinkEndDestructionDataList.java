
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

public class LinkEndDestructionDataList
		extends
		ArrayList<fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData> {
	public LinkEndDestructionDataList() {
		super();
	}

	public LinkEndDestructionData getValue(int i) {
		return (LinkEndDestructionData) get(i);
	}

	public void addValue(LinkEndDestructionData v) {
		add(v);
	}

	public void addValue(int i, LinkEndDestructionData v) {
		add(i, v);
	}

	public void setValue(int i, LinkEndDestructionData v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // LinkEndDestructionDataList
