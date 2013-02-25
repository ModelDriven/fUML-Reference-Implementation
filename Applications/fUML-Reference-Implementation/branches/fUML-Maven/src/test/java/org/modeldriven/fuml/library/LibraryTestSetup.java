/*
 * Copyright (c) 2008 Lockheed Martin Corporation.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

/**
 * Provides helper methods for the Foundational Model Library JUnit tests.
 * 
 * Specifically, the input and output ParameterValueList objects are
 * initialized here to simulate the fUML system.  Because there are many
 * JUnit test classes which have to initialize these two parameters, centralize
 * the initialization here in case a change is required.
 */
public class LibraryTestSetup {
	
	/**
	 * Simulate the fUML system by initializing the ParameterValueList
	 * before calling the doBody() method on the library classes.
	 * 
	 * @param inputParameters
	 */
	public ParameterValueList setupInputParameterList() {
		ParameterValueList inputParameters = new ParameterValueList();				
		return inputParameters;
	}
	
	/**
	 * Simulate the fUML system by initializing the ParameterValueList
	 * before calling the doBody() method on the library classes.  The 
	 * OpaqueBehaviorExecution creates ParameterValue objects for the
	 * output parameters.
	 * 
	 * @param inputParameters
	 */	
	public ParameterValueList setupOutputParameterList() {
		ParameterValueList outputParameters = new ParameterValueList();
		outputParameters.add(new ParameterValue());
		return outputParameters;		
	}	
}
