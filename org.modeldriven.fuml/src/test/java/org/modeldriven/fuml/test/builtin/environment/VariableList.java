
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

package org.modeldriven.fuml.test.builtin.environment;

import java.util.ArrayList;

public class VariableList extends ArrayList<org.modeldriven.fuml.test.builtin.environment.Variable> {
	public VariableList() {
		super();
	}

	public Variable getValue(int i) {
		return (Variable) get(i);
	}

	public void addValue(Variable v) {
		add(v);
	}

	public void addValue(int i, Variable v) {
		add(i, v);
	}

	public void setValue(int i, Variable v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // VariableList
