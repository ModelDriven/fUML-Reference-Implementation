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
package org.modeldriven.fuml.test.model;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.test.FUMLTest;

import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.syntax.commonbehavior.Behavior;

public abstract class AbstractExecutionTestCase extends FUMLTest {

	protected static Log log = LogFactory.getLog(AbstractExecutionTestCase.class);

	protected static Environment environment;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (AbstractExecutionTestCase.environment == null) {
			AbstractExecutionTestCase.environment = Environment.getInstance();
			File file = new File(getFileName());
			assertTrue("file '" + getFileName() + "' does not exist", file.exists());
			Fuml.load(file, getNamespaceURI());
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		AbstractExecutionTestCase.environment.locus.extensionalValues.clear();
	}

	protected ParameterValueList execute(String activityName) {
		Behavior behavior = environment.findBehavior(activityName);
		if (behavior == null)
			throw new RuntimeException("invalid behavior, " + activityName);
		log.info("executing behavior: " + behavior.name);
		ExecutionEnvironment execution = new ExecutionEnvironment(environment);
		return execution.execute(behavior);
	}
	
	public abstract String getFileName();

	public abstract String getNamespaceURI();
}
