/*
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */
package fuml.syntax.activities;

import java.util.ArrayList;

public class ActivityGroupList extends ArrayList<ActivityGroup> {
	public ActivityGroupList() {
		super();
	}

	public ActivityGroup getValue(int i) {
		return (ActivityGroup) get(i);
	}

	public void addValue(ActivityGroup v) {
		add(v);
	}

	public void addValue(int i, ActivityGroup v) {
		add(i, v);
	}

	public void setValue(int i, ActivityGroup v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
}
