
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

public class ExpansionNode extends
		fuml.syntax.activities.ObjectNode {

	public fuml.syntax.actions.ExpansionRegion regionAsOutput = null;
	public fuml.syntax.actions.ExpansionRegion regionAsInput = null;

	public void _setRegionAsInput(
			fuml.syntax.actions.ExpansionRegion regionAsInput) {
		this.regionAsInput = regionAsInput;
	} // _setRegionAsInput

	public void _setRegionAsOutput(
			fuml.syntax.actions.ExpansionRegion regionAsOutput) {
		this.regionAsOutput = regionAsOutput;
	} // _setRegionAsOutput

} // ExpansionNode
