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
package fuml.syntax.actions;

public class RaiseExceptionAction extends Action {

	/**
	 * An InputPin whose value becomes the exception object
	 */
	public InputPin exception;

	public void setException(InputPin exception) {
		super.addInput(exception);
		this.exception = exception;
	}

}
