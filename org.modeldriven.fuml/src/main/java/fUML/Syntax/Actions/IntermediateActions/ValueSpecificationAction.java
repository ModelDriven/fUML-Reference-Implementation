
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

package fUML.Syntax.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class ValueSpecificationAction extends
		fUML.Syntax.Actions.BasicActions.Action {

	public fUML.Syntax.Classes.Kernel.ValueSpecification value = null;
	public fUML.Syntax.Actions.BasicActions.OutputPin result = null;

	public void setValue(fUML.Syntax.Classes.Kernel.ValueSpecification value) {
		this.value = value;
	} // setValue

	public void setResult(fUML.Syntax.Actions.BasicActions.OutputPin result) {
		super.addOutput(result);
		this.result = result;
	} // setResult

} // ValueSpecificationAction
