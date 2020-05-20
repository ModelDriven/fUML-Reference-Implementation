/*
 * Copyright 2020 Model Driven Solutions, Inc.
 * Copyright 2020 CEA LIST.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.activities;

import fuml.Debug;
import fuml.semantics.loci.ChoiceStrategy;
import fuml.semantics.values.Value;
import fuml.syntax.activities.ExceptionHandler;
import fuml.syntax.activities.ExceptionHandlerList;
import fuml.syntax.activities.ExecutableNode;

public abstract class ExecutableNodeActivation extends ActivityNodeActivation {

	public void propagateException(Value exception) {
		// If there is no matching exception handler for the given exception, then propagate
		// the exception to either the containing node activation or the activity execution, as
		// appropriate.
		// If there is a matching exception handler, then use it to catch the exception. 
		// (If there is more than one matching handler, then choose one non-deterministically.)
		
		ExceptionHandlerList matchingExceptionHandlers = 
				this.getMatchingExceptionHandlers(exception);
		
		if (matchingExceptionHandlers.size() == 0) {
			this.terminate();
			if (this.group.containingNodeActivation != null) {
				this.group.containingNodeActivation.propagateException(exception);
			} else {
				this.group.activityExecution.propagateException(exception);
			}			
		} else {
			ChoiceStrategy strategy = (ChoiceStrategy) this.getExecutionLocus().
					factory.getStrategy("choice");
			ExceptionHandler handler = matchingExceptionHandlers.getValue(
					strategy.choose(matchingExceptionHandlers.size()) - 1);
			this.handle(exception, handler);
		}
	}

	public ExceptionHandlerList getMatchingExceptionHandlers(Value exception) {
		// Return the set of exception handlers that have an exception type
		// for which the given exception is an instance.
		
		ExceptionHandlerList handlers = ((ExecutableNode)this.node).handler;
		ExceptionHandlerList matchingHandlers = new ExceptionHandlerList();
		
		for (int i = 0; i < handlers.size(); i++) {
			ExceptionHandler handler = handlers.getValue(i);
			
			boolean noMatch = true;
			int j = 1;
			while (noMatch & j <= handler.exceptionType.size()) {
				if (exception.isInstanceOf(handler.exceptionType.getValue(j - 1))) {
					matchingHandlers.addValue(handler);
					noMatch = false;
				}
				j = j + 1;
			}
			
		}
		
		return matchingHandlers;
	}

	public void handle(Value exception, ExceptionHandler handler) {
		// Offer the given exception to the body of the given exception handler
		// on its exception input node.
		
		Debug.println("[handle] action = " + this.node.name + ", exception = " + exception);
		
		ActivityNodeActivation handlerBodyActivation = 
				this.group.getNodeActivation(handler.handlerBody);
		ActivityNodeActivation inputActivation = 
				handlerBodyActivation.group.getNodeActivation(handler.exceptionInput);
		
		ObjectToken token = new ObjectToken();
		token.value = exception;
		inputActivation.addToken(token);
		
		handlerBodyActivation.receiveOffer();
	}

}
