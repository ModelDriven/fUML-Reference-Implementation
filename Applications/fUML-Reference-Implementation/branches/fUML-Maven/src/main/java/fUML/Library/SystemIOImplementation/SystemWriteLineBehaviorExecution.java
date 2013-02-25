
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

package fUML.Library.SystemIOImplementation;

import fUML.Debug;
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
			fUML.Library.ChannelImplementation.StandardOutputChannelObject standardOutput = new fUML.Library.ChannelImplementation.StandardOutputChannelObject();
			standardOutput.open();
			standardOutput.writeLine(values.getValue(i).toString());
		}
	} // doBody

	public fUML.Semantics.Classes.Kernel.Value new_() {
		// Create a new execution object.

		return new SystemWriteLineBehaviorExecution();
	} // new_

} // SystemWriteLineBehaviorExecution
