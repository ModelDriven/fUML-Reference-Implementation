
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

package fUML.Semantics.CommonBehaviors.Communications;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import java.util.Iterator;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Loci.LociL1.*;

public class ObjectActivation_Behavior {

	public ObjectActivation self = null;

	public ObjectActivation_Behavior(ObjectActivation self) {
		this.self = self;
	}

	int signalCount = 0;
	public void _startObjectBehavior() {
		// *** This should start the EventDispatchLoop ***

		while (this.signalCount > 0) {
			this.self.dispatchNextEvent();
			signalCount = signalCount - 1;
		}
	} // _startObjectBehavior

	public void _send(
			fUML.Semantics.CommonBehaviors.Communications.ArrivalSignal signal) {
		// Signal the arrival of a new signal instance in the event pool.
		// *** This should send an ArrivalSignal to the EventDispatchLoop. ***
		
		this.signalCount = this.signalCount + 1;
		if (this.signalCount == 1) {
			this._startObjectBehavior();
		}
	} // _send

}
