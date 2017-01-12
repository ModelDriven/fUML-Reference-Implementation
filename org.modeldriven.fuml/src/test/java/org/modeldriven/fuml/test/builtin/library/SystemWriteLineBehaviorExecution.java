
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.library;

import fUML.Debug;

import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.common.Status;

import UMLPrimitiveTypes.*;

public class SystemWriteLineBehaviorExecution extends
		fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {

	public void doBody(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList inputParameters,
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList outputParameters) {
		// Print the string representation all values of the first input
		// parameter.

		fUML.Semantics.Classes.Kernel.ValueList values = inputParameters
				.getValue(0).values;

		for (int i = 0; i < values.size(); i++) {
			// Debug.println(">>>>>>>> " + values.getValue(i));
			StandardOutputChannelObject standardOutput = new StandardOutputChannelObject();
			Status status = new Status(this.locus, "");
			standardOutput.open(status);
			standardOutput.writeLine(values.getValue(i).toString(), status);
		}
	} // doBody

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new execution object.

		return new SystemWriteLineBehaviorExecution();
	} // new_

} // SystemWriteLineBehaviorExecution
