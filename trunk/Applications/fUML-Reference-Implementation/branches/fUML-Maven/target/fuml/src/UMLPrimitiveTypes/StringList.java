
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

package UMLPrimitiveTypes;

import java.util.ArrayList;

public class StringList extends ArrayList<String> {
	public StringList() {
		super();
	}

	public String getValue(int i) {
		return (String) get(i);
	}

	public void addValue(String v) {
		add(v);
	}

	public void addValue(int i, String v) {
		add(i, v);
	}

	public void setValue(int i, String v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // StringList
