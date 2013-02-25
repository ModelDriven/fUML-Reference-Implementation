package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class SuperCallTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(SuperCallTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(SuperCallTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testSuperCall() throws Exception {
        log.info("testSuperCall");
        initTestEnv.testSuite.testSuperCall("ForkJoin", 
        		"HelloWorld2");
        log.info("done");
    }
    
}