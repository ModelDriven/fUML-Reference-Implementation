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

import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.ClassifierList;
import fuml.syntax.commonstructure.Element;

public class ExceptionHandler extends Element {

	/**
	 * An ObjectNode within the handlerBody. When the ExceptionHandler catches an
	 * exception, the exception token is placed on this ObjectNode, causing the
	 * handlerBody to execute.
	 */
	public ObjectNode exceptionInput;

	/**
	 * The Classifiers whose instances the ExceptionHandler catches as exceptions.
	 * If an exception occurs whose type is any exceptionType, the ExceptionHandler
	 * catches the exception and executes the handlerBody.
	 */
	public ClassifierList exceptionType = new ClassifierList();

	/**
	 * An ExecutableNode that is executed if the ExceptionHandler catches an
	 * exception.
	 */
	public ExecutableNode handlerBody;

	/**
	 * The ExecutableNode protected by the ExceptionHandler. If an exception
	 * propagates out of the protectedNode and has a type matching one of the
	 * exceptionTypes, then it is caught by this ExceptionHandler.
	 */
	public ExecutableNode protectedNode;

	public void setExceptionInput(ObjectNode exceptionInput) {
		this.exceptionInput = exceptionInput;
	}

	public void addExceptionType(Classifier exceptionType) {
		this.exceptionType.addValue(exceptionType);
	}

	public void setHandlerBody(ExecutableNode handlerBody) {
		this.handlerBody = handlerBody;
	}
	
}