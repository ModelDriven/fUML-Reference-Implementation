
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

package fuml.syntax.actions;

public class DestroyLinkAction extends
		fuml.syntax.actions.WriteLinkAction {

	public fuml.syntax.actions.LinkEndDestructionDataList endData = new fuml.syntax.actions.LinkEndDestructionDataList();

	public void addEndData(
			fuml.syntax.actions.LinkEndData endData) {
		super.addEndData(endData);
		this.endData.addValue((LinkEndDestructionData) endData);
	} // addEndData

} // DestroyLinkAction
