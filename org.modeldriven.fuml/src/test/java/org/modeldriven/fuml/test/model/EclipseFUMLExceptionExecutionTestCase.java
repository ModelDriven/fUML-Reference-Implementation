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

import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.simpleclassifiers.StringValue;
import junit.framework.Test;

public class EclipseFUMLExceptionExecutionTestCase extends AbstractExecutionTestCase {

	public static Test suite() {
		return FUMLTestSetup.newTestSetup(EclipseFUMLExceptionExecutionTestCase.class);
	}
	
	@Override
	public String getFileName() {
		return "./target/test-classes/uml/fUML-Exception-Tests.uml";
	}

	@Override
	public String getNamespaceURI() {
		return "http://org.modeldriven.fuml/test/uml/papyrus/fUML-Exception-Tests";
	}

	public void testException001() throws Exception {
		ParameterValueList output = execute("Test001");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertEqualValues("output", output.get(0), "Test001 - Exception raised and caught ");
	}

	public void testException002() throws Exception {
		ParameterValueList output = execute("Test002");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertEqualValues("output", output.get(0), "Test 002 - Before the raising of an exception");
	}

	public void testException003() throws Exception {
		ParameterValueList output = execute("Test003");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertEqualValues("output", output.get(0), true);
	}

	public void testException004() throws Exception {
		ParameterValueList output = execute("Test004");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertTrue(((StringValue) output.get(0).values.getValue(0)).value
				.equals("Test 004 - Exception caught by handler 1")
				|| ((StringValue) output.get(0).values.getValue(0)).value
						.equals("Test 004 - Exception caught by handler 2"));
	}

	public void testException005() throws Exception {
		ParameterValueList output = execute("Test005");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertEqualValues("output", output.get(0), "Test 005 - Exception Raised - After exception handling");
	}

	public void testException006() throws Exception{
		ParameterValueList output = execute("Test006");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 1, output.size());
		assertEqualValues("output", output.get(0), new Object[] {});
	}
	
	public void testException007() throws Exception{
		ParameterValueList output = execute("Test007");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 2, output.size());
		assertEqualValues("output_0", output.get(0), "Exception");
		assertEqualValues("output_1", output.get(1), true);
	}
	
	public void testException008() throws Exception {
		ParameterValueList output = execute("Test008");
		log.info("done");
		assertNotNull(output);
		assertEquals("output.size()", 2, output.size());
		assertEqualValues("output_0", output.get(0), true);
		assertEqualValues("output_1", output.get(1), "Exception");
	}

}
