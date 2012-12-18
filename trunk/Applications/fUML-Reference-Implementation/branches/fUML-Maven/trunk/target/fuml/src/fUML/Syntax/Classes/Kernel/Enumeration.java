
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

public class Enumeration extends fUML.Syntax.Classes.Kernel.DataType {

	public fUML.Syntax.Classes.Kernel.EnumerationLiteralList ownedLiteral = new fUML.Syntax.Classes.Kernel.EnumerationLiteralList();

	public void addOwnedLiteral(
			fUML.Syntax.Classes.Kernel.EnumerationLiteral ownedLiteral) {
		super.addOwnedMember(ownedLiteral);

		this.ownedLiteral.addValue(ownedLiteral);
		ownedLiteral._setEnumeration(this);
	} // addOwnedLiteral

} // Enumeration
