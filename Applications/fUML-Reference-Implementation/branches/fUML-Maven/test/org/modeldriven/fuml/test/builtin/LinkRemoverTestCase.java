package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LinkRemoverTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkRemoverTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkRemoverTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLinkRemover() throws Exception {
        log.info("testLinkRemover");
        initTestEnv.testSuite.testLinkRemover();
        log.info("done");
    }
    
}