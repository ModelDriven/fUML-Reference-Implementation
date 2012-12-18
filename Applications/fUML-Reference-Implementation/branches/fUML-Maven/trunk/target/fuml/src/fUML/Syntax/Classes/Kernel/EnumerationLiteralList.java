
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

import java.util.ArrayList;

public class EnumerationLiteralList extends
		ArrayList<fUML.Syntax.Classes.Kernel.EnumerationLiteral> {
	public EnumerationLiteralList() {
		super();
	}

	public EnumerationLiteral getValue(int i) {
		return (EnumerationLiteral) get(i);
	}

	public void addValue(EnumerationLiteral v) {
		add(v);
	}

	public void addValue(int i, EnumerationLiteral v) {
		add(i, v);
	}

	public void setValue(int i, EnumerationLiteral v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // EnumerationLiteralList
