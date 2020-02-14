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
package fuml.semantics.activities;

import java.util.ArrayList;

public class ExceptionHandlerActivationList extends ArrayList<ExceptionHandlerActivation>{
	
	public ExceptionHandlerActivationList() {
		super();
	}

	public ExceptionHandlerActivation getValue(int i) {
		return (ExceptionHandlerActivation) get(i);
	}

	public void addValue(ExceptionHandlerActivation v) {
		add(v);
	}

	public void addValue(int i, ExceptionHandlerActivation v) {
		add(i, v);
	}

	public void setValue(int i, ExceptionHandlerActivation v) {
		set(i, v);
	}

	public void removeValue(int i) {
		remove(i);
	}

}
