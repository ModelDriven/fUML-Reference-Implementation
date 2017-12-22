
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

package fuml.syntax.simpleclassifiers;

public class Enumeration extends fuml.syntax.simpleclassifiers.DataType {

	public fuml.syntax.simpleclassifiers.EnumerationLiteralList ownedLiteral = new fuml.syntax.simpleclassifiers.EnumerationLiteralList();

	public void addOwnedLiteral(
			fuml.syntax.simpleclassifiers.EnumerationLiteral ownedLiteral) {
		super.addOwnedMember(ownedLiteral);

		this.ownedLiteral.addValue(ownedLiteral);
		ownedLiteral._setEnumeration(this);
	} // addOwnedLiteral

} // Enumeration
