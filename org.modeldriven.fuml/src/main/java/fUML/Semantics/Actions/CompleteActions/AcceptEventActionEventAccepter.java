
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;
import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;
import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.CommonBehaviors.Communications.SignalEventOccurrence;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Actions.IntermediateActions.*;
import fUML.Semantics.Loci.*;

public class AcceptEventActionEventAccepter extends
		fUML.Semantics.CommonBehaviors.Communications.EventAccepter {

	public fUML.Semantics.Actions.CompleteActions.AcceptEventActionActivation actionActivation = null;

	public void accept(
			fUML.Semantics.CommonBehaviors.Communications.EventOccurrence eventOccurrence) {
		// Accept a signal event occurrence. Forward the signal instance to the action activation.

		if (eventOccurrence instanceof SignalEventOccurrence) {
			this.actionActivation.accept(((SignalEventOccurrence)eventOccurrence).signalInstance);
		}
	} // accept

	public boolean match(
			fUML.Semantics.CommonBehaviors.Communications.EventOccurrence eventOccurrence) {
		// Return true if the given event occurrence is a signal event occurrence and the
		// signal instance matches a trigger of the accept action of the action activation.

		boolean matches = false;
		if (eventOccurrence instanceof SignalEventOccurrence) {
			matches = this.actionActivation.
					match(((SignalEventOccurrence)eventOccurrence).signalInstance);
		}
		return matches;
	} // match

} // AcceptEventActionEventAccepter
