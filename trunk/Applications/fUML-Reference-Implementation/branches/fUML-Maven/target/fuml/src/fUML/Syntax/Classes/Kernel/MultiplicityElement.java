
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

public class MultiplicityElement extends fUML.Syntax.Classes.Kernel.Element {

	public boolean isOrdered = false;
	public boolean isUnique = true;
	public UnlimitedNatural upper = new UnlimitedNatural(1);
	public int lower = 1;
	public fUML.Syntax.Classes.Kernel.ValueSpecification upperValue = null;
	public fUML.Syntax.Classes.Kernel.ValueSpecification lowerValue = null;

	public MultiplicityElement() {
		// This constructor sets upperValue and lowerValue consistently with the
		// initializers for upper and lower.

		this.setUpper(1);
		this.setLower(1);
	} // MultiplicityElement

	public void setIsOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	} // setIsOrdered

	public void setIsUnique(boolean isUnique) {
		this.isUnique = isUnique;
	} // setIsUnique

	public void setUpperValue(
			fUML.Syntax.Classes.Kernel.ValueSpecification upperValue) {
		this.upperValue = upperValue;

		if (upperValue instanceof LiteralUnlimitedNatural) {
			this.upper = ((LiteralUnlimitedNatural) upperValue).value;
		}
	} // setUpperValue

	public void setLowerValue(
			fUML.Syntax.Classes.Kernel.ValueSpecification lowerValue) {
		this.lowerValue = lowerValue;

		if (lowerValue instanceof LiteralInteger) {
			this.lower = ((LiteralInteger) lowerValue).value;
		}
	} // setLowerValue

	public void setUpper(int upper) {
		// Note: This is a convenience operation that may be used _instead_ of
		// setUpperValue, not in addition to it.

		this.upper = new UnlimitedNatural(upper);
		this.upperValue = new LiteralUnlimitedNatural();
		((LiteralUnlimitedNatural) this.upperValue).setValue(this.upper);
	} // setUpper

	public void setLower(int lower) {
		// Note: This is a convenience operation that may be used _instead_ of
		// setLowerValue, not in addition to it.

		this.lower = lower;
		this.lowerValue = new LiteralInteger();
		((LiteralInteger) this.lowerValue).setValue(this.lower);
	} // setLower

} // MultiplicityElement
