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

import junit.framework.TestCase;

import org.modeldriven.fuml.environment.Environment;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.Loci.LociL1.Locus;

/**
 * Base test fixture for the Foundational Model Library JUnit tests.
 * 
 * Specifically, the input and output ParameterValueList objects are
 * initialized here to simulate the fUML system and an execution locus is
 * provided with primitive types registered.
 */
public abstract class LibraryTest extends TestCase {
	
	protected ParameterValueList inputParameters;
	protected ParameterValueList outputParameters;
	protected Locus locus;
	
	protected OpaqueBehaviorExecution obj;

	/**
	 * Set up the ParameterValueLists to simulate the fUML system before calling
	 * the doBody() method on the library classes.
	 */
	@Override
	public void setUp() {
		this.inputParameters = new ParameterValueList();
		this.outputParameters = new ParameterValueList();
		this.outputParameters.add(new ParameterValue());
		this.locus = Environment.getInstance().locus;
	}
	
	protected void doBody() {
		this.obj.locus = this.locus;
		this.obj.doBody(this.inputParameters, this.outputParameters);
	}
	
}
