/*
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.syntax.classification;

import java.util.ArrayList;

public class StructuralFeatureList extends ArrayList<fuml.syntax.classification.StructuralFeature> {
	public StructuralFeatureList() {
		super();
	}

	public StructuralFeature getValue(int i) {
		return (StructuralFeature) get(i);
	}

	public void addValue(StructuralFeature v) {
		add(v);
	}

	public void addValue(int i, StructuralFeature v) {
		add(i, v);
	}

	public void setValue(int i, StructuralFeature v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
} // FeatureList
