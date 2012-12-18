
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

public abstract class ActivityEdge extends
		fUML.Syntax.Classes.Kernel.RedefinableElement {

	public fUML.Syntax.Activities.IntermediateActivities.Activity activity = null;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityNode source = null;
	public fUML.Syntax.Activities.IntermediateActivities.ActivityNode target = null;
	public fUML.Syntax.Classes.Kernel.ValueSpecification guard = null;
	public fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode inStructuredNode = null;

	public void setTarget(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode target) {
		this.target = target;
		target._addIncoming(this);
	} // setTarget

	public void setSource(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode source) {
		this.source = source;
		source._addOutgoing(this);
	} // setSource

	public void setGuard(fUML.Syntax.Classes.Kernel.ValueSpecification guard) {
		if (guard != null) {
			super.addOwnedElement(guard);
		}

		this.guard = guard;
	} // setGuard

	public void _setActivity(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity) {
		this.activity = activity;
	} // _setActivity

	public void _setInStructuredNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode inStructuredNode) {
		this.inStructuredNode = inStructuredNode;
	} // _setInStructuredNode

} // ActivityEdge
