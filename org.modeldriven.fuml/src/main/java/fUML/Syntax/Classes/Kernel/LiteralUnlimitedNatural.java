
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

public class LiteralUnlimitedNatural extends
		fUML.Syntax.Classes.Kernel.LiteralSpecification {

	public UnlimitedNatural value = new UnlimitedNatural(0);

	public void setValue(UnlimitedNatural value) {
		this.value = value;
	} // setValue

	public void setValue(int value) {
		this.setValue(new UnlimitedNatural(value));
	} // setValue

} // LiteralUnlimitedNatural
