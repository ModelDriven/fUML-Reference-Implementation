
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

public class ExpansionRegion
		extends
		fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode {

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind mode = fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind.iterative;
	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNodeList outputElement = new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNodeList();
	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNodeList inputElement = new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNodeList();

	public void setMode(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind mode) {
		this.mode = mode;
	} // setMode

	public void addInputElement(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode inputElement) {
		this.inputElement.addValue(inputElement);
		inputElement._setRegionAsInput(this);
	} // addInputElement

	public void addOutputElement(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode outputElement) {
		this.outputElement.addValue(outputElement);
		outputElement._setRegionAsOutput(this);
	} // addOutputElement

} // ExpansionRegion
