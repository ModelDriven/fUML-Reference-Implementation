
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

public abstract class ActivityEdge extends
		fuml.syntax.classification.RedefinableElement {

	public fuml.syntax.activities.Activity activity = null;
	public fuml.syntax.activities.ActivityNode source = null;
	public fuml.syntax.activities.ActivityNode target = null;
	public fuml.syntax.values.ValueSpecification guard = null;
	public fuml.syntax.actions.StructuredActivityNode inStructuredNode = null;

	public void setTarget(
			fuml.syntax.activities.ActivityNode target) {
		this.target = target;
		target._addIncoming(this);
	} // setTarget

	public void setSource(
			fuml.syntax.activities.ActivityNode source) {
		this.source = source;
		source._addOutgoing(this);
	} // setSource

	public void setGuard(fuml.syntax.values.ValueSpecification guard) {
		if (guard != null) {
			super.addOwnedElement(guard);
		}

		this.guard = guard;
	} // setGuard

	public void _setActivity(
			fuml.syntax.activities.Activity activity) {
		this.activity = activity;
	} // _setActivity

	public void _setInStructuredNode(
			fuml.syntax.actions.StructuredActivityNode inStructuredNode) {
		this.inStructuredNode = inStructuredNode;
	} // _setInStructuredNode

} // ActivityEdge
