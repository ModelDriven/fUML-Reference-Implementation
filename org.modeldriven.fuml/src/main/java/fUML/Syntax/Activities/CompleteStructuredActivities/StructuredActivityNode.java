
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

package fUML.Syntax.Activities.CompleteStructuredActivities;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public class StructuredActivityNode extends
		fUML.Syntax.Actions.BasicActions.Action {

	public fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList node = new fUML.Syntax.Activities.IntermediateActivities.ActivityNodeList();
	public fUML.Syntax.Activities.IntermediateActivities.Activity activity = null;
	public boolean mustIsolate = false;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList edge = new fUML.Syntax.Activities.IntermediateActivities.ActivityEdgeList();
	public fUML.Syntax.Actions.BasicActions.OutputPinList structuredNodeOutput = new fUML.Syntax.Actions.BasicActions.OutputPinList();
	public fUML.Syntax.Actions.BasicActions.InputPinList structuredNodeInput = new fUML.Syntax.Actions.BasicActions.InputPinList();

	public void setMustIsolate(boolean mustIsolate) {
		this.mustIsolate = mustIsolate;
	} // setMustIsolate

	public void addNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		this.node.addValue(node);
		node._setInStructuredNode(this);
	} // addNode

	public void addEdge(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge edge) {
		this.edge.addValue(edge);
		edge._setInStructuredNode(this);
	} // addEdge

	public void addStructuredNodeOutput(
			fUML.Syntax.Actions.BasicActions.OutputPin structuredNodeOutput) {
		this.addOutput(structuredNodeOutput);
		this.structuredNodeOutput.addValue(structuredNodeOutput);
	} // addStructuredNodeOutput

	public void addStructuredNodeInput(
			fUML.Syntax.Actions.BasicActions.InputPin structuredNodeInput) {
		this.addInput(structuredNodeInput);
		this.structuredNodeInput.addValue(structuredNodeInput);
	} // addStructuredNodeInput

	public void _setContext(fUML.Syntax.Classes.Kernel.Classifier context) {
		super._setContext(context);
		for (ActivityNode node : this.node) {
			if (node instanceof Action) {
				((Action) node)._setContext(context);
			}
		}
	} // _setContext

	public void _setActivity(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity) {
		super._setActivity(activity);
		this.activity = activity;
	} // _setActivity

} // StructuredActivityNode
