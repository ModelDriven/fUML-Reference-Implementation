/*****************************************************************************
* Copyright (c) 2020 CEA LIST.
*
* Licensed under the Academic Free License version 3.0 
* (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
* in the file entitled Licensing-Information. 
*
* Contributors:
*  Jeremie TATIBOUET <jeremie.tatibouet@cea.fr>
*
*****************************************************************************/
package fuml.syntax.activities;

import java.util.ArrayList;

public class ExceptionHandlerList extends ArrayList<ExceptionHandler> {
	
	public ExceptionHandlerList() {
		super();
	}
	
	public ExceptionHandler getValue(int i) {
		return (ExceptionHandler) get(i);
	}

	public void addValue(ExceptionHandler v) {
		add(v);
	}

	public void addValue(int i, ExceptionHandler v) {
		add(i, v);
	}

	public void setValue(int i, ExceptionHandler v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}
	
}
