
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

public abstract class ActivityNode extends
		fuml.syntax.classification.RedefinableElement {

	public fuml.syntax.actions.StructuredActivityNode inStructuredNode = null;
	public fuml.syntax.activities.Activity activity = null;
	public fuml.syntax.activities.ActivityEdgeList outgoing = new fuml.syntax.activities.ActivityEdgeList();
	public fuml.syntax.activities.ActivityEdgeList incoming = new fuml.syntax.activities.ActivityEdgeList();

	public void _setActivity(
			fuml.syntax.activities.Activity activity) {
		this.activity = activity;
	} // _setActivity

	public void _setInStructuredNode(
			fuml.syntax.actions.StructuredActivityNode inStructuredNode) {
		this.inStructuredNode = inStructuredNode;
	} // _setInStructuredNode

	public void _addIncoming(
			fuml.syntax.activities.ActivityEdge incoming) {
		this.incoming.addValue(incoming);
	} // _addIncoming

	public void _addOutgoing(
			fuml.syntax.activities.ActivityEdge outgoing) {
		this.outgoing.addValue(outgoing);
	} // _addOutgoing

} // ActivityNode
