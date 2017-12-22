
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

import fuml.syntax.activities.ActivityNode;

public class StructuredActivityNode extends
		fuml.syntax.actions.Action {

	public fuml.syntax.activities.ActivityNodeList node = new fuml.syntax.activities.ActivityNodeList();
	public fuml.syntax.activities.Activity activity = null;
	public boolean mustIsolate = false;
	public fuml.syntax.activities.ActivityEdgeList edge = new fuml.syntax.activities.ActivityEdgeList();
	public fuml.syntax.actions.OutputPinList structuredNodeOutput = new fuml.syntax.actions.OutputPinList();
	public fuml.syntax.actions.InputPinList structuredNodeInput = new fuml.syntax.actions.InputPinList();

	public void setMustIsolate(boolean mustIsolate) {
		this.mustIsolate = mustIsolate;
	} // setMustIsolate

	public void addNode(
			fuml.syntax.activities.ActivityNode node) {
		this.node.addValue(node);
		node._setInStructuredNode(this);
	} // addNode

	public void addEdge(
			fuml.syntax.activities.ActivityEdge edge) {
		this.edge.addValue(edge);
		edge._setInStructuredNode(this);
	} // addEdge

	public void addStructuredNodeOutput(
			fuml.syntax.actions.OutputPin structuredNodeOutput) {
		this.addOutput(structuredNodeOutput);
		this.structuredNodeOutput.addValue(structuredNodeOutput);
	} // addStructuredNodeOutput

	public void addStructuredNodeInput(
			fuml.syntax.actions.InputPin structuredNodeInput) {
		this.addInput(structuredNodeInput);
		this.structuredNodeInput.addValue(structuredNodeInput);
	} // addStructuredNodeInput

	public void _setContext(fuml.syntax.classification.Classifier context) {
		super._setContext(context);
		for (ActivityNode node : this.node) {
			if (node instanceof Action) {
				((Action) node)._setContext(context);
			}
		}
	} // _setContext

	public void _setActivity(
			fuml.syntax.activities.Activity activity) {
		super._setActivity(activity);
		this.activity = activity;
	} // _setActivity

} // StructuredActivityNode
