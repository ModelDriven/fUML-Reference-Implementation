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
package fuml.semantics.actions;

import fuml.semantics.values.ValueList;
import fuml.syntax.actions.RaiseExceptionAction;

public class RaiseExceptionActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		ValueList values = this.takeTokens(((RaiseExceptionAction)node).exception);
		this.propagateException(values.getValue(0));
	}

}
