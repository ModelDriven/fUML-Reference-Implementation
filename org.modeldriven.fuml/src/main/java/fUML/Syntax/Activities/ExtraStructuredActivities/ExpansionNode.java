
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

package fUML.Syntax.Activities.ExtraStructuredActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class ExpansionNode extends
		fUML.Syntax.Activities.IntermediateActivities.ObjectNode {

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion regionAsOutput = null;
	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion regionAsInput = null;

	public void _setRegionAsInput(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion regionAsInput) {
		this.regionAsInput = regionAsInput;
	} // _setRegionAsInput

	public void _setRegionAsOutput(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion regionAsOutput) {
		this.regionAsOutput = regionAsOutput;
	} // _setRegionAsOutput

} // ExpansionNode
