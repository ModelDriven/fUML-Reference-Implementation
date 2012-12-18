package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class PolymorphicCallTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(PolymorphicCallTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(PolymorphicCallTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testPolymorphicCall() throws Exception {
        log.info("testPolymorphicCall");
        initTestEnv.testSuite.testPolymorphicOperationCall("ForkJoin", 
        		"HelloWorld2");
        log.info("done");
    }
    
}