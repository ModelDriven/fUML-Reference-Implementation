
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

package fUML.Semantics.Loci.LociL1;

import java.util.ArrayList;

public class SemanticStrategyList extends
		ArrayList<fUML.Semantics.Loci.LociL1.SemanticStrategy> {
	public SemanticStrategyList() {
		super();
	}

	public SemanticStrategy getValue(int i) {
		return (SemanticStrategy) get(i);
	}

	public void addValue(SemanticStrategy v) {
		add(v);
	}

	public void addValue(int i, SemanticStrategy v) {
		add(i, v);
	}

	public void setValue(int i, SemanticStrategy v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // SemanticStrategyList
