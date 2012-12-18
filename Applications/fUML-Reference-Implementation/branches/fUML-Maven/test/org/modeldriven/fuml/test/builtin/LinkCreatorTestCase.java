package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LinkCreatorTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkCreatorTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkCreatorTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLinkCreator() throws Exception {
        log.info("testLinkCreator");
        initTestEnv.testSuite.testLinkCreator();
        log.info("done");
    }
    
}