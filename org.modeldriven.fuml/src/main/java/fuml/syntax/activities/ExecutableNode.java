
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 * Copyright 2020 CEA LIST.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.syntax.activities;

public abstract class ExecutableNode extends fuml.syntax.activities.ActivityGroup {

	/**
	 * A set of ExceptionHandlers that are examined if an exception propagates out of the ExceptionNode.
	 */
	public ExceptionHandlerList handler = new ExceptionHandlerList();
	
	public void addExceptionHandler(ExceptionHandler handler) {
		this.addOwnedElement(handler);
		this.handler.addValue(handler);
		handler.protectedNode = this;
	}
	
} // ExecutableNode
