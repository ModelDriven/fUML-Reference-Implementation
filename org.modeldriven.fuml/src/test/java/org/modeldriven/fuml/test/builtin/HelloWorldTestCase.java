package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.syntax.actions.ValueSpecificationAction;
import fuml.syntax.activities.Activity;
import fuml.syntax.commonstructure.Element;
import fuml.syntax.values.LiteralString;
import junit.framework.Test;

/**
 * 
 */
public class HelloWorldTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(HelloWorldTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(HelloWorldTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testHelloWorld() throws Exception {
        log.info("testHelloWorld");
        this.testSuite.testHelloWorld();
        
         // ensure standard output channel has 1 instance
        ExtensionalValueList extent = findExtent("StandardOutputChannel");
        assertTrue(extent != null);
        assertTrue(extent.size() == 1);

        // ensure HelloWorld2 has an extent but no instances
        Element element = findElement("HelloWorld2");
        assertTrue(element != null);
        assertTrue(element instanceof Activity);
        Activity hello = (Activity)element;
        // should have 1 edge
        assertTrue(hello.edge != null);
        assertTrue(hello.edge.size() == 1);
        // ensure first activity node is a value spec action and it
        // has a literal with our value
        Object obj = hello.node.get(0);
        assertTrue(obj instanceof ValueSpecificationAction);
        ValueSpecificationAction action = (ValueSpecificationAction)obj;
        assertTrue(action != null);
        LiteralString literal = (LiteralString)action.value;
        assertTrue("Hello World!".equals(literal.value));        
        
        log.info("done");
    }
    
}