package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class StructuredNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(StructuredNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(StructuredNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testStructuredNode() throws Exception {
        log.info("testStructuredNode");
        initTestEnv.testSuite.testStructuredNode();
        log.info("done");
    }
    
}