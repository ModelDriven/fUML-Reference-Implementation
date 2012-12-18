package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LoopNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LoopNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LoopNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLoopNode() throws Exception {
        log.info("testLoopNode");
        initTestEnv.testSuite.testLoopNode();
        log.info("done");
    }
    
}