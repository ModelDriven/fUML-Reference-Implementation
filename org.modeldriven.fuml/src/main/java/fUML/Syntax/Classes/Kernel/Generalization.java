
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

public class Generalization extends fUML.Syntax.Classes.Kernel.Element {

	public boolean isSubstitutable = true;
	public fUML.Syntax.Classes.Kernel.Classifier specific = null;
	public fUML.Syntax.Classes.Kernel.Classifier general = null;

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.isSubstitutable = isSubstitutable;
	} // setIsSubstitutable

	public void setGeneral(fUML.Syntax.Classes.Kernel.Classifier general) {
		this.general = general;
	} // setGeneral

	public void _setSpecific(fUML.Syntax.Classes.Kernel.Classifier specific) {
		this.specific = specific;
	} // _setSpecific

} // Generalization
