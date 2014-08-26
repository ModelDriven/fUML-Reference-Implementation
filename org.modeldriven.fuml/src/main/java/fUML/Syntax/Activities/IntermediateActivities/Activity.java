
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

import fUML.Syntax.Activities.CompleteStructuredActivities.*;

public class Activity extends
		fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior {

	public fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNodeList structuredNode = new fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNodeList();
	public fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList node = new fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList();
	public boolean isReadOnly = false;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList edge = new fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList();

	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	} // setIsReadOnly

	public void addNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		super.addOwnedElement(node);

		this.node.add(node);
		node._setActivity(this);

		if (node instanceof StructuredActivityNode) {
			this.structuredNode.add((StructuredActivityNode) node);
		}

	} // addNode

	public void addEdge(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge edge) {
		super.addOwnedElement(edge);

		this.edge.addValue(edge);
		edge._setActivity(this);
	} // addEdge

	public void _setContext(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier context) {
		// Note: The context of an activity should be set only _after_ all nodes
		// have been added to the activity.

		super._setContext(context);

		for (int i = 0; i < this.node.size(); i++) {
			ActivityNode node = this.node.getValue(i);
			if (node instanceof fUML.Syntax.Actions.BasicActions.Action) {
				((fUML.Syntax.Actions.BasicActions.Action) node)
						._setContext(context);
			}
		}
	} // _setContext

} // Activity
