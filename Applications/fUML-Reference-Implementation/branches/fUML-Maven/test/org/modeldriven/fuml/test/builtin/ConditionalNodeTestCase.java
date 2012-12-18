package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class ConditionalNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(ConditionalNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ConditionalNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testConditionalNode() throws Exception {
        log.info("testConditionalNode");
        initTestEnv.testSuite.testConditionalNode();
        log.info("done");
    }
    
}