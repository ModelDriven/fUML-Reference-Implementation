
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

public class DataType extends fUML.Syntax.Classes.Kernel.Classifier {

	public fUML.Syntax.Classes.Kernel.PropertyList ownedAttribute = new fUML.Syntax.Classes.Kernel.PropertyList();

	public void addOwnedAttribute(
			fUML.Syntax.Classes.Kernel.Property ownedAttribute) {
		super.addAttribute(ownedAttribute);
		super.addOwnedMember(ownedAttribute);

		this.ownedAttribute.addValue(ownedAttribute);
		ownedAttribute._setDatatype(this);
	} // addOwnedAttribute

} // DataType
