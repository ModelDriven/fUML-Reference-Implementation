
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

public abstract class ActivityNode extends
		fUML.Syntax.Classes.Kernel.RedefinableElement {

	public fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode inStructuredNode = null;
	public fUML.Syntax.Activities.IntermediateActivities.Activity activity = null;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList outgoing = new fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList();
	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList incoming = new fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList();

	public void _setActivity(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity) {
		this.activity = activity;
	} // _setActivity

	public void _setInStructuredNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode inStructuredNode) {
		this.inStructuredNode = inStructuredNode;
	} // _setInStructuredNode

	public void _addIncoming(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge incoming) {
		this.incoming.addValue(incoming);
	} // _addIncoming

	public void _addOutgoing(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge outgoing) {
		this.outgoing.addValue(outgoing);
	} // _addOutgoing

} // ActivityNode
