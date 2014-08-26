package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class FireAgainTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(FireAgainTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(FireAgainTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testFireAgain() throws Exception {
        log.info("testFireAgain");
        initTestEnv.testSuite.testFireAgain();
        log.info("done");
    }
    
}