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

import fuml.semantics.activities.ActivityExecution;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.structuredclassifiers.Class_;

/**
 * 
 */
public class EclipseExecutionTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(EclipseExecutionTestCase.class);
    
    private static Environment environment; // JUnit creates a new test class for every test! 
    private static String namespaceURI = "http://org.modeldriven.fuml/test/uml/magicdraw/fUML-Tests";
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(EclipseExecutionTestCase.class);
    }
    
    public void setUp() throws Exception {
        if (EclipseExecutionTestCase.environment == null)
        {    
            EclipseExecutionTestCase.environment = Environment.getInstance();
            String filename = "./target/test-classes/uml/fUML-Tests.uml";
            File file = new File(filename);
            assertTrue("file '" + filename + "' does not exist", file.exists());
            Fuml.load(file, namespaceURI);
            //Fuml.loadIncrementally(file, namespaceURI);
        }
    }
    
    public void tearDown() throws Exception {
    	environment.locus.extensionalValues.clear();
    }
    
    public void testCopier() throws Exception {
        ParameterValueList output = execute("Copier");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("output", output.get(0), 0);
    }
    
    public void testCopierCaller() throws Exception {
    	ParameterValueList output = execute("CopierCaller");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("output", output.get(0), 888);
     }
  
    public void testSimpleDecision() throws Exception {
    	ParameterValueList output = execute("SimpleDecision");
        log.info("done");
        
        assertEquals("output.size()", 2, output.size());
        assertEqualValues("output_0", output.get(0), 0);
        assertEqualValues("output_1", output.get(1));
     }
       
    public void testForkJoin() throws Exception {
    	execute("ForkJoin");
        log.info("done");
    }
    
    public void testDecisionJoin() throws Exception {
    	ParameterValueList output = execute("DecisionJoin");
        log.info("done");

        assertEquals("output.size()", 1, output.size());
        assertEqualValues("output", output.get(0), 0, 1);
}
    
    public void testForkMerge() throws Exception {
    	ParameterValueList output = execute("ForkMerge");
        log.info("done");

        assertEquals("output.size()", 1, output.size());
        assertEqualValues("output", output.get(0), 0, 0);
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
        assertTrue("value instanceof StructureValue", value instanceof StructuredValue);
        assertTrue("value.getTypes().isEmpty()", value.getTypes().isEmpty());
        assertTrue("value.getFeatureValues().isEmpty()", ((StructuredValue)value).getFeatureValues().isEmpty());
    }
    
    public void testTestCompositeObjectDestroyer() throws Exception {
        ParameterValueList output = execute("TestCompositeObjectDestroyer");
        log.info("done");
        
        assertEquals("output.size()", 4, output.size());
        
        ValueList compositeOut = output.get(0).values;
        assertEquals("compositeOut.size()", 1, compositeOut.size());
        Value value = compositeOut.get(0);
        assertTrue("compositeOut instanceof StructuredValue", value instanceof StructuredValue);
        assertTrue("compositeOut.getTypes().isEmpty()", value.getTypes().isEmpty());
        assertTrue("compositeOut.getFeatureValues().isEmpty()", ((StructuredValue)value).getFeatureValues().isEmpty());
      
        ValueList object1Out = output.get(1).values;
        assertEquals("object1Out.size()", 1, object1Out.size());
        value = object1Out.get(0);
        assertTrue("object1Out instanceof StructuredValue", value instanceof StructuredValue);
        assertTrue("object1Out.getTypes().isEmpty()", value.getTypes().isEmpty());
        assertTrue("object1Out.getFeatureValues().isEmpty()", ((StructuredValue)value).getFeatureValues().isEmpty());
        
        ValueList object2Out = output.get(2).values;
        assertEquals("object2Out.size()", 1, object2Out.size());
        value = object2Out.get(0);
        assertTrue("object2Out instanceof StructuredValue", value instanceof StructuredValue);
        assertTrue("object2Out.getTypes().isEmpty()", value.getTypes().isEmpty());
        assertTrue("object2Out.getFeatureValues().isEmpty()", ((StructuredValue)value).getFeatureValues().isEmpty());
        
        ValueList assocOut = output.get(3).values;
        assertEquals("assocOut.size()", 0, assocOut.size());
    }
    
    public void testTestClassWriterReader() throws Exception {
        ParameterValueList output = execute("TestClassWriterReader");
        log.info("done");
        
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("output", output.get(0), 999);
   }

    public void testTestClassAttributeWriter() throws Exception {
        ParameterValueList output = execute("TestClassAttributeWriter");
        log.info("done");
        
        assertTrue("One output value", output.size() == 1 && output.get(0).values.size() == 1);
        Value value = output.get(0).values.get(0);
        assertTrue("value instanceof Reference", value instanceof Reference);
        FeatureValueList featureValues = ((Reference)value).getFeatureValues();
        assertEquals("featureValues.size()", 2, featureValues.size());
        assertEqualValues("y", featureValues.get(1).values, 0, 0);
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
        assertEqualValues("Coper.output", output.get(0), 0);
        assertEqualValues("CoperCaller.output", output.get(1), 888);
        assertEqualValues("SimpleDecision_0.output_0", output.get(2), 0);
        assertEqualValues("SimpleDecision_0.output_1", output.get(3));
        assertEqualValues("SimpleDecision_1.output_0", output.get(4));
        assertEqualValues("SimpleDecision_1.output_1", output.get(5), 1);
        assertEqualValues("DecisionJoin.output", output.get(6), 0, 1);
        assertEqualValues("ForkMerge", output.get(7), 0, 0);
        assertEqualValues("ForkMergeData", output.get(8), 0, 0);
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
        assertEqualValues("y", yValues, 2, 0);
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
        assertEqualValues("x", featureValues.get(1).values, 1);
        assertEqualValues("y", featureValues.get(2).values, 2);
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
    
    public void testTestCallSend() throws Exception {
    	execute("TestCallSend");
    	log.info("done");
    	
        Class_ callSenderClass = (Class_)environment.findElementById("TestCallSender");
        ExtensionalValueList extent = environment.locus.getExtent(callSenderClass);
        
        assertEquals("extent.size()", 1, extent.size());
        FeatureValueList featureValues = extent.get(0).getFeatureValues();
        assertEquals("featureValues.size()", 1, featureValues.size());
        assertEqualValues("value", featureValues.get(0).values, 0);
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
    
    public void testTestCentralBuffer() throws Exception {
    	ParameterValueList output = execute("TestCentralBuffer");
    	log.info("done");
    	
    	assertNotNull(output);
    	assertEquals("output.size()", 1, output.size());
    	assertEqualValues("output", output.get(0), 0, 0);
    }

    public void testTestDataStore() throws Exception {
    	ParameterValueList output = execute("TestDataStore");
    	log.info("done");
    	
    	assertNotNull(output);
    	assertEquals("output.size()", 1, output.size());
    	assertEqualValues("output", output.get(0), 1, 2);
    }

    public void testTestClassUnmarshaller() throws Exception {
    	ParameterValueList output = execute("TestClassUnmarshaller");
    	log.info("done");
    	
    	assertNotNull(output);
    	assertEquals("output.size()", 2, output.size());
    	assertEqualValues("x", output.get(0), 0);
    	assertEqualValues("y", output.get(1), 1, 2);
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