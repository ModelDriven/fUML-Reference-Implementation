
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

package fuml.syntax.classification;

import java.util.ArrayList;

public class PropertyList extends
		ArrayList<fuml.syntax.classification.Property> {
	public PropertyList() {
		super();
	}

	public Property getValue(int i) {
		return (Property) get(i);
	}

	public void addValue(Property v) {
		add(v);
	}

	public void addValue(int i, Property v) {
		add(i, v);
	}

	public void setValue(int i, Property v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // PropertyList
