
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

public abstract class ObjectNode extends
		fUML.Syntax.Activities.IntermediateActivities.ActivityNode {

	public fUML.Syntax.Classes.Kernel.TypedElement typedElement = new fUML.Syntax.Classes.Kernel.TypedElement();

	public void setType(fUML.Syntax.Classes.Kernel.Type type) {
		this.typedElement.type = type;
	} // setType

} // ObjectNode
