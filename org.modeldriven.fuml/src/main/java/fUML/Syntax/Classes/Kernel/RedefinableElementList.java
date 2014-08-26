
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

public class RedefinableElementList extends
		ArrayList<fUML.Syntax.Classes.Kernel.RedefinableElement> {
	public RedefinableElementList() {
		super();
	}

	public RedefinableElement getValue(int i) {
		return (RedefinableElement) get(i);
	}

	public void addValue(RedefinableElement v) {
		add(v);
	}

	public void addValue(int i, RedefinableElement v) {
		add(i, v);
	}

	public void setValue(int i, RedefinableElement v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // RedefinableElementList
