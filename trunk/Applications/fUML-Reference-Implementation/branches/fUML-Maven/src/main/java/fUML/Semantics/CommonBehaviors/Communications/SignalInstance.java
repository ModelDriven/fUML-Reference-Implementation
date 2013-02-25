
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

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Loci.*;

public class SignalInstance extends fUML.Semantics.Classes.Kernel.CompoundValue {

	public fUML.Syntax.CommonBehaviors.Communications.Signal type = null;

	public fUML.Syntax.Classes.Kernel.ClassifierList getTypes() {
		// Return the single type of this signal instance.

		ClassifierList types = new ClassifierList();

		types.addValue(this.type);

		return types;
	} // getTypes

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new signal instance with no type or feature values.

		return new SignalInstance();
	} // new_

	public fUML.Semantics.Classes.Kernel.Value copy() {
		// Create a new signal instance with the same type and feature values as
		// this signal instance.

		SignalInstance newValue = (SignalInstance) (super.copy());

		newValue.type = this.type;

		return newValue;
	} // copy

} // SignalInstance
