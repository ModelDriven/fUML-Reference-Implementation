package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class ExpansionRegionTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(ExpansionRegionTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ExpansionRegionTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testExpansionRegion() throws Exception {
        log.info("testExpansionRegion");
        initTestEnv.testSuite.testExpansionRegion();
        log.info("done");
    }
    
}