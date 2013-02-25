
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

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class EnumerationLiteral extends
		fUML.Syntax.Classes.Kernel.InstanceSpecification {

	public fUML.Syntax.Classes.Kernel.Enumeration enumeration = null;
	public fUML.Syntax.Classes.Kernel.Enumeration classifier = null;

	public void _setEnumeration(
			fUML.Syntax.Classes.Kernel.Enumeration enumeration) {
		super.addClassifier(enumeration);
		this.classifier = enumeration;
		this.enumeration = enumeration;
	} // _setEnumeration

} // EnumerationLiteral
