
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

package fUML.Syntax.Activities.IntermediateActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class DecisionNode extends
		fUML.Syntax.Activities.IntermediateActivities.ControlNode {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior decisionInput = null;
	public fUML.Syntax.Activities.IntermediateActivities.ObjectFlow decisionInputFlow = null;

	public void setDecisionInput(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior decisionInput) {
		this.decisionInput = decisionInput;
	} // setDecisionInput

	public void setDecisionInputFlow(
			fUML.Syntax.Activities.IntermediateActivities.ObjectFlow decisionInputFlow) {
		this.decisionInputFlow = decisionInputFlow;
	} // setDecisionInputFlow

} // DecisionNode
