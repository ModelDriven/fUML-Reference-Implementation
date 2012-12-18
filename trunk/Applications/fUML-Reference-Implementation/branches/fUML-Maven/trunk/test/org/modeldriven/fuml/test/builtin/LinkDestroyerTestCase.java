package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LinkDestroyerTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkDestroyerTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkDestroyerTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLinkDestroyer() throws Exception {
        log.info("testLinkDestroyer");
        initTestEnv.testSuite.testLinkDestroyer();
        log.info("done");
    }
    
}