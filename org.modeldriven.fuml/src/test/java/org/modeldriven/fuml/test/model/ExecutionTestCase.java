package org.modeldriven.fuml.test.model;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class ExecutionTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(ExecutionTestCase.class);
    
    private static Environment environment; // JUnit creates a new test class for every test! 
    private static String namespaceURI = "http://org.modeldriven.fuml/test/uml/magicdraw/fUML-Tests.uml";
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ExecutionTestCase.class);
    }
    
    public void setUp() throws Exception {
        if (ExecutionTestCase.environment == null)
        {    
            ExecutionTestCase.environment = Environment.getInstance();
            String filename = "./target/test-classes/mdxml/fUML-Tests.mdxml";
            File file = new File(filename);
            assertTrue("file '" + filename + "' does not exist", file.exists());
            Fuml.load(file, namespaceURI);
            //Fuml.loadIncrementally(file, namespaceURI);
        }
    }
    
    public void testCopier() throws Exception {
        execute("Copier");
        log.info("done");
    }
    
    public void testCopierCaller() throws Exception {
        execute("CopierCaller");
        log.info("done");
    }
  
    public void testSimpleDecision() throws Exception {
        execute("SimpleDecision");
        log.info("done");
    }
       
    public void testForkJoin() throws Exception {
        execute("ForkJoin");
        log.info("done");
    }
    
    public void testDecisionJoin() throws Exception {
        execute("DecisionJoin");
        log.info("done");
    }
    
    public void testForkMerge() throws Exception {
        execute("ForkMerge");
        log.info("done");
    }

    public void testTestClassExtentReader() throws Exception {
        execute("TestClassExtentReader");
        log.info("done");
    }
    
    public void testSelfReader() throws Exception {
        execute("SelfReader");
        log.info("done");
    }

    public void testIdentityTester() throws Exception {
        execute("IdentityTester");
        log.info("done");
    }
 
    public void testTestClassObjectCreator() throws Exception {
        execute("TestClassObjectCreator");
        log.info("done");
    }

    public void testObjectDestroyer() throws Exception {
        execute("ObjectDestroyer");
        log.info("done");
    }
    public void testTestClassWriterReader() throws Exception {
        execute("TestClassWriterReader");
        log.info("done");
    }

    public void testTestClassAttributeWriter() throws Exception {
        execute("TestClassAttributeWriter");
        log.info("done");
    }
    
    public void testTestGeneralizationAssembly() throws Exception {
    	execute("TestGeneralizationAssembly");
    	log.info("done");
    	
    	Class_ specificClass = (Class_) environment.findElementById("TestGeneralizationAssembly-Specific");
    	ExtensionalValueList extent = environment.locus.getExtent(specificClass);

    	assertTrue("Specific should have two properties", extent.size() == 1 && extent.get(0).featureValues.size() == 2);
    }

    public void testTestSimpleActivities() throws Exception {
        execute("TestSimpleActivities");
        log.info("done");
    }
    
    public void testTestClassValueRemover() throws Exception {
    	execute("TestClassAttributeValueRemover");
    	log.info("done");
    }

    public void testTestAssociationEndWriterReader() throws Exception {
    	execute("TestAssociationEndWriterReader");
    	log.info("done");
    }
    
    public void testTestClassReclassifier() throws Exception {
    	execute("TestClassReclassifier");
    	log.info("done");
    }
    
    public void testTestSpecializedSignalSend() throws Exception {
    	execute("TestSpecializedSignalSend");
    	log.info("done");
    }
    
    public void testNodeEnabler() throws Exception {
    	ParameterValueList parameterValues = execute("TestNodeEnabler");
    	log.info("done");
    	
    	assertTrue("Should be one output parameter value", parameterValues.size() == 1);
    	assertTrue("Output parameter should have a single value", parameterValues.get(0).values.size() == 1);
    }
    
   public void testActiveClassBehaviorSender() throws Exception {
    	execute("ActiveClassBehaviorSender");
    	log.info("done");
    }
    
    private ParameterValueList execute(String activityName)
    {
        Behavior behavior = environment.findBehavior(activityName);
        if (behavior == null)
            throw new RuntimeException("invalid behavior, " + activityName);
        log.info("executing behavior: " + behavior.name);
        ExecutionEnvironment execution = new ExecutionEnvironment(environment);
        return execution.execute(behavior);
    }
    
}