
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

package fuml.syntax.activities;

import fuml.syntax.actions.StructuredActivityNode;

public class Activity extends
		fuml.syntax.commonbehavior.Behavior {

	public fuml.syntax.actions.StructuredActivityNodeList structuredNode = new fuml.syntax.actions.StructuredActivityNodeList();
	public fuml.syntax.activities.ActivityNodeList node = new fuml.syntax.activities.ActivityNodeList();
	public boolean isReadOnly = false;
	public fuml.syntax.activities.ActivityEdgeList edge = new fuml.syntax.activities.ActivityEdgeList();
	public fuml.syntax.activities.ActivityGroupList group = new fuml.syntax.activities.ActivityGroupList();

	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	} // setIsReadOnly

	public void addNode(
			fuml.syntax.activities.ActivityNode node) {
		if (!this.node.contains(node)) {
			super.addOwnedElement(node);
	
			this.node.add(node);
			node._setActivity(this);
		}
		
		if (node instanceof StructuredActivityNode &&
				!this.structuredNode.contains(node)) {
			this.structuredNode.add((StructuredActivityNode) node);
		}

	} // addNode
	
	public void addStructuredNode(fuml.syntax.actions.StructuredActivityNode node) {
		this.addNode(node);
	}
	
	public void addGroup(fuml.syntax.activities.ActivityGroup group) {
		this.group.add(group);
	}

	public void addEdge(
			fuml.syntax.activities.ActivityEdge edge) {
		super.addOwnedElement(edge);

		this.edge.addValue(edge);
		edge._setActivity(this);
	} // addEdge

	public void _setContext(
			fuml.syntax.commonbehavior.BehavioredClassifier context) {
		// Note: The context of an activity should be set only _after_ all nodes
		// have been added to the activity.

		super._setContext(context);

		for (int i = 0; i < this.node.size(); i++) {
			ActivityNode node = this.node.getValue(i);
			if (node instanceof fuml.syntax.actions.Action) {
				((fuml.syntax.actions.Action) node)
						._setContext(context);
			}
		}
	} // _setContext

} // Activity
