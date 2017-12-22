
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

package fuml.semantics.actions;

import java.util.ArrayList;

public class TokenSetList extends
		ArrayList<fuml.semantics.actions.TokenSet> {
	public TokenSetList() {
		super();
	}

	public TokenSet getValue(int i) {
		return (TokenSet) get(i);
	}

	public void addValue(TokenSet v) {
		add(v);
	}

	public void addValue(int i, TokenSet v) {
		add(i, v);
	}

	public void setValue(int i, TokenSet v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // TokenSetList
