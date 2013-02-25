package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LinkReaderTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkReaderTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkReaderTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLinkReader() throws Exception {
        log.info("testLinkReader");
        initTestEnv.testSuite.testLinkReader();
        log.info("done");
    }
    
}