package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class SimpleActivitiesTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(SimpleActivitiesTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(SimpleActivitiesTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testSimpleActivities() throws Exception {
        log.info("testSimpleActivities");
        initTestEnv.testSuite.testSimpleActivites();
        log.info("done");
    }
    
}