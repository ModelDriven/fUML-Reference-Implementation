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

import fUML.Semantics.Activities.IntermediateActivities.ActivityExecution;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Semantics.Classes.Kernel.FeatureValueList;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
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
    
    public void tearDown() throws Exception {
    	environment.locus.extensionalValues.clear();
    }
    
    private static void assertIntegerValues(String label, ParameterValue parameterValue, int... expectedValues) throws Exception {
    	assertIntegerValues(label, parameterValue.values, expectedValues);
    }
    
    private static void assertIntegerValues(String label, ValueList values, int... expectedValues) throws Exception {
    	assertEquals(label + ": values.size()", expectedValues.length, values.size());
    	for (int i = 0; i < values.size(); i++) {
    		Value value = values.get(i);
    		assertTrue(label + ": value[" + i + "] instanceof IntegerValue", value instanceof IntegerValue);
    		assertEquals(label + ": value[" + i + "]", expectedValues[i], ((IntegerValue)value).value);
    	}
    }
    
    private static void assertAllEquals(String label, Value expectedValue, ValueList values) {
    	for (int i = 0; i < values.size(); i++) {
    		// NOTE: Uses fUML-specific Value::equals method.
    		assertTrue(label + "value[" + i + "]", values.get(i).equals(expectedValue));
    	}
    }
    
    public void testCopier() throws Exception {
        ParameterValueList output = execute("Copier");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 0);
    }
    
    public void testCopierCaller() throws Exception {
    	ParameterValueList output = execute("CopierCaller");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 888);
     }
  
    public void testSimpleDecision() throws Exception {
    	ParameterValueList output = execute("SimpleDecision");
        log.info("done");
        
        assertEquals("output.size()", 2, output.size());
        assertIntegerValues("output_0", output.get(0), 0);
        assertIntegerValues("output_1", output.get(1));
     }
       
    public void testForkJoin() throws Exception {
    	execute("ForkJoin");
        log.info("done");
    }
    
    public void testDecisionJoin() throws Exception {
    	ParameterValueList output = execute("DecisionJoin");
        log.info("done");

        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 1, 0);
}
    
    public void testForkMerge() throws Exception {
    	ParameterValueList output = execute("ForkMerge");
        log.info("done");

        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 0, 0);
    }

    public void testTestClassExtentReader() throws Exception {
    	ParameterValueList output = execute("TestClassExtentReader");
        log.info("done");
        
        Class_ testClass = (Class_)environment.findElementById("TestClass");
    	assertEquals("output.size()", 2, output.size());
    	assertTrue("object.hasType(testClass)", 
    			output.get(0).values.size() == 1 && output.get(0).values.get(0).hasType(testClass));
    	Value objectValue = output.get(0).values.get(0);
    	for (Value value: output.get(1).values) {
    		if (value.equals(objectValue)) { // NOTE: Uses fUML-specific Value::equals method.
    			return;
    		}
    	}
    	fail("extent.contains(object)");
    }
    
    public void testSelfReader() throws Exception {
        ParameterValueList output = execute("SelfReader");
        log.info("done");
        
        assertTrue("Two output values", 
        		output.size() == 2 && output.get(0).values.size() == 1 && output.get(1).values.size() == 1);
        Value value1 = output.get(0).values.get(0);
        assertTrue("value1.referent instanceof ActivityExecution", 
        		value1 instanceof Reference && ((Reference)value1).referent instanceof ActivityExecution);
        Value value2 = output.get(1).values.get(0);
        assertTrue("value2.value", value2 instanceof BooleanValue && ((BooleanValue)value2).value);
    }

    public void testTestClassIdentityTester() throws Exception {
        ParameterValueList output = execute("TestClassIdentityTester");
        log.info("done");
        assertTrue("One output value", output.size() == 1 && output.get(0).values.size() == 1);
        Value value = output.get(0).values.get(0);
        assertFalse("value", value instanceof BooleanValue && ((BooleanValue)value).value);
    }
 
    public void testTestClassObjectCreator() throws Exception {
        ParameterValueList output = execute("TestClassObjectCreator");
        log.info("done");
        
        Class_ testClass = (Class_)environment.findElementById("TestClass");
        assertEquals("output.size()", 1, output.size());
        ValueList values = output.get(0).values;
        assertTrue("value.hasType(testClass)", values.size() == 1 && values.get(0).hasType(testClass));
    }

    public void testTestClassObjectDestroyer() throws Exception {
        ParameterValueList output = execute("TestClassObjectDestroyer");
        log.info("done");
        
        assertTrue("One output value", output.size() == 1 && output.get(0).values.size() == 1);
        Value value = output.get(0).values.get(0);
        assertTrue("value.getTypes().isEmpty()", value.getTypes().isEmpty());
    }
    public void testTestClassWriterReader() throws Exception {
        ParameterValueList output = execute("TestClassWriterReader");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 999);
   }

    public void testTestClassAttributeWriter() throws Exception {
        ParameterValueList output = execute("TestClassAttributeWriter");
        log.info("done");
        
        assertTrue("One output value", output.size() == 1 && output.get(0).values.size() == 1);
        Value value = output.get(0).values.get(0);
        assertTrue("value instanceof Reference", value instanceof Reference);
        FeatureValueList featureValues = ((Reference)value).getFeatureValues();
        assertEquals("featureValues.size()", 2, featureValues.size());
        assertIntegerValues("y", featureValues.get(1).values, 0, 0);
    }
    
    public void testTestGeneralizationAssembly() throws Exception {
    	execute("TestGeneralizationAssembly");
    	log.info("done");
    	
    	Class_ specificClass = (Class_) environment.findElementById("TestGeneralizationAssembly-Specific");
    	ExtensionalValueList extent = environment.locus.getExtent(specificClass);

    	assertEquals("extent.size()", 1, extent.size());
    	assertEquals("featureValues.size()", 2, extent.get(0).featureValues.size());
    }

    public void testTestSimpleActivities() throws Exception {
    	ParameterValueList output = execute("TestSimpleActivities");
        log.info("done");
        
        assertEquals("output.size()", 9, output.size());        
        assertIntegerValues("Coper.output", output.get(0), 0);
        assertIntegerValues("CoperCaller.output", output.get(1), 888);
        assertIntegerValues("SimpleDecision_0.output_0", output.get(2), 0);
        assertIntegerValues("SimpleDecision_0.output_1", output.get(3));
        assertIntegerValues("SimpleDecision_1.output_0", output.get(4));
        assertIntegerValues("SimpleDecision_1.output_1", output.get(5), 1);
        assertIntegerValues("DecisionJoin.output", output.get(6), 1, 0);
        assertIntegerValues("ForkMerge", output.get(7), 0, 0);
        assertIntegerValues("ForkMergeData", output.get(8), 0, 0);
    }
    
    public void testTestClassValueRemover() throws Exception {
    	ParameterValueList output = execute("TestClassAttributeValueRemover");
    	log.info("done");
    	
        Class_ testClass = (Class_)environment.findElementById("TestClass");
        assertEquals("output.size()", 1, output.size());
        ValueList values = output.get(0).values;
        assertTrue("value.hasType(testClass)", values.size() == 1 && values.get(0).hasType(testClass));
        FeatureValueList featureValues = ((Reference)values.get(0)).getFeatureValues();
        assertEquals("featureValues.size()", 2, featureValues.size());
        ValueList yValues = featureValues.get(1).values;
        assertIntegerValues("y", yValues, 2, 0);
    }

    public void testTestAssociationEndWriterReader() throws Exception {
    	ParameterValueList output = execute("TestAssociationEndWriterReader");
    	log.info("done");
    	
        assertEquals("output.size()", 3, output.size());
        assertEquals("expected.size()", 1, output.get(2).values.size());
        Value expected = output.get(2).values.get(0);
        
        ValueList output1 = output.get(0).values;
        assertEquals("output1.size()", 2, output1.size());
        assertAllEquals("output1", expected, output1);
        
        ValueList output2 = output.get(1).values;
        assertEquals("output2.size()", 1, output2.size());
        assertAllEquals("output2", expected, output2);
    }
    
    public void testTestClassReclassifier() throws Exception {
    	ParameterValueList output = execute("TestClassReclassifier");
    	log.info("done");
    	
        Class_ subclass = (Class_)environment.findElementById("Subclass2");
 
        assertEquals("output.size()", 1, output.size());        
        ValueList values = output.get(0).values;
        assertTrue("value.hasType(subclass)", values.size() == 1 && values.get(0).hasType(subclass));
        FeatureValueList featureValues = ((Reference)values.get(0)).getFeatureValues();
        assertEquals("featureValues.size()", 3, featureValues.size());
        assertEquals("b: values.size()", 0, featureValues.get(0).values.size());
        assertIntegerValues("x", featureValues.get(1).values, 1);
        assertIntegerValues("y", featureValues.get(2).values, 2);
    }
    
    public void testTestSpecializedSignalSend() throws Exception {
    	execute("TestSpecializedSignalSend");
    	log.info("done");
    	
        Class_ signalReceiverClass = (Class_)environment.findElementById("TestSignalReceiver");
        ExtensionalValueList extent = environment.locus.getExtent(signalReceiverClass);
        
        assertEquals("extent.size()", 1, extent.size());
        FeatureValueList featureValues = extent.get(0).getFeatureValues();
        assertEquals("featureValues.size()", 1, featureValues.size());
        assertEquals("signal: values.size()", 1, featureValues.get(0).values.size());
    }
    
    public void testNodeEnabler() throws Exception {
    	ParameterValueList output = execute("TestNodeEnabler");
    	log.info("done");
    	
    	assertTrue("One output value", output.size() == 1 && output.get(0).values.size() == 1);
    }
    
   public void testActiveClassBehaviorSender() throws Exception {
    	execute("ActiveClassBehaviorSender");
    	log.info("done");

        Class_ activeClass = (Class_)environment.findElementById("ActiveClass");
        ExtensionalValueList extent = environment.locus.getExtent(activeClass);
        
        assertEquals("extent.size()", 1, extent.size());
        FeatureValueList featureValues = extent.get(0).getFeatureValues();
        assertEquals("featureValues.size()", 2, featureValues.size());
        assertEquals("signal1: values.size()", 1, featureValues.get(0).values.size());
        assertEquals("signal2: values.size()", 1, featureValues.get(1).values.size());
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