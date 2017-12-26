package org.modeldriven.fuml.test.model;

import java.util.List;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.OpaqueBehavior;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.syntax.actions.CallBehaviorAction;
import fuml.syntax.actions.ExpansionRegion;
import fuml.syntax.activities.Activity;
import fuml.syntax.activities.ActivityEdge;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.activities.ObjectNode;
import fuml.syntax.classification.Feature;
import fuml.syntax.classification.Parameter;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.commonstructure.TypedElement;
import fuml.syntax.simpleclassifiers.DataType;
import fuml.syntax.values.LiteralInteger;
import fuml.syntax.values.LiteralUnlimitedNatural;



/**
 * 
 */
public class ModelTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(ModelTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ModelTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testCreateModel() throws Exception {
    	
        log.info("testCreateModel");
        Repository model = Repository.INSTANCE;
        log.info("returned an instance");
        
        //Object lock = new Object();
        //synchronized(lock) {
        //	lock.wait(300000);
        //}
    }
    
    public void testActivity() throws Exception {    
        Classifier activityClassifier = Repository.INSTANCE.findClassifier(Activity.class.getSimpleName());
        assertTrue("could not find class 'Activity'", 
        		activityClassifier != null);
    }

    public void testActivityEdge() throws Exception {    
    	Class_ activityEdgeClassifier = (Class_)Repository.INSTANCE.findClassifier(ActivityEdge.class.getSimpleName());
        assertTrue("could not find class 'ActivityEdge'", 
        		activityEdgeClassifier != null);
        Property isLeaf = activityEdgeClassifier.findProperty("isLeaf");
        assertTrue("No ownedAttribute 'isLeaf' found for class 'Activity'", isLeaf != null);
        assertTrue("Expected singular ownedAttribute 'isLeaf' for class 'Activity'", 
        		isLeaf.isSingular());
    }    
    
    public void testOpaqueBehavior() throws Exception {    
    	Class_ opaqueBehaviorClassifier = (Class_)Repository.INSTANCE.findClassifier(OpaqueBehavior.class.getSimpleName());
        assertTrue("could not find class 'OpaqueBehavior'", opaqueBehaviorClassifier != null);
        Property visibility = opaqueBehaviorClassifier.findProperty("visibility");
        assertTrue("No ownedAttribute 'visibility' found for class 'OpaqueBehavior'", visibility != null);        
    }
    
    public void testOpaqueExpression() throws Exception {    
    	Class_ opaqueExpressionClassifier = (Class_)Repository.INSTANCE.findClassifier("OpaqueExpression");
        assertTrue("could not find class 'OpaqueExpression'", opaqueExpressionClassifier != null);
        Property visibility = opaqueExpressionClassifier.findProperty("visibility");
        assertTrue("No ownedAttribute 'visibility' found for class 'OpaqueExpression'", visibility != null);       
    }

    public void testParameter() throws Exception {    
        log.info("testParameter");
        Class_ parameter = (Class_)Repository.INSTANCE.findClassifier(Parameter.class.getSimpleName());
        assertTrue("could not find class 'Parameter'", parameter != null);
        assertTrue("expected parameter as non abstract class", !parameter.isAbstract());
        Property multiplicityElement = parameter.findProperty("multiplicityElement");
        assertTrue("No ownedAttribute 'multiplicityElement' expected for class 'Parameter'", multiplicityElement == null);
        Property direction = parameter.findProperty("direction");
        assertTrue("The ownedAttribute 'direction' expected for class 'Parameter'", direction != null);
        assertTrue("Expected 'Parameter.direction' as required", 
        		direction.isRequired());
        assertTrue("Expected default value for 'Parameter.direction'", 
        		direction.hasPropertyDefaultValue());        
    }

    public void testCallBehaviorAction() throws Exception {    
        log.info("testCallBehaviorAction");
        Class_ actionClassifier = (Class_)Repository.INSTANCE.findClassifier(CallBehaviorAction.class.getSimpleName());
        assertTrue("could not find classifier 'CallBehaviorAction'", actionClassifier != null);
        Property behavior = actionClassifier.findProperty("behavior");
        assertTrue("No ownedAttribute 'behavior' found for class 'CallBehaviorAction'", behavior != null);
        assertTrue("Expected ownedAttribute 'behavior' as required for class 'CallBehaviorAction'", 
        		behavior.isRequired());        
    }

    public void testLiteralInteger() throws Exception {    
        log.info("testLiteralInteger");
        Class_ integerClassifier = (Class_)Repository.INSTANCE.findClassifier(LiteralInteger.class.getSimpleName());
        assertTrue("could not find class for 'LiteralInteger'", integerClassifier != null);
        Property nameProp = integerClassifier.findProperty("name");
        assertTrue("No ownedAttribute 'name' found for class 'LiteralInteger'", nameProp != null);
        Classifier nameType = nameProp.findType();        
    }

    public void testLiteralUnlimitedNatural() throws Exception {    
        log.info("testLiteralUnlimitedNatural");
        Class_ classifier = (Class_)Repository.INSTANCE.findClassifier(LiteralUnlimitedNatural.class.getSimpleName());
        assertTrue("could not find class for 'LiteralUnlimitedNatural'", classifier != null);
        Property nameProp = classifier.findProperty("name");
        assertTrue("No ownedAttribute 'name' found for class 'LiteralUnlimitedNatural'", nameProp != null);
        Classifier nameType = nameProp.findType();
        assertTrue("LiteralUnlimitedNatural.name is not an instance of DataType", 
                nameType.getDelegate() instanceof DataType);
        Property valueProp = classifier.findProperty("value");
        assertTrue("No ownedAttribute 'value' found for class 'LiteralUnlimitedNatural'", valueProp != null);
        Classifier valueType = valueProp.findType();
        assertNotNull("LiteralUnlimitedNatural.value is null", 
        		valueType);        
    }    

    public void testTypedElement() throws Exception {    
        log.info("testTypedElement");
        Class_ typedElementClassifier = (Class_)Repository.INSTANCE.findClassifier(TypedElement.class.getSimpleName());
        assertTrue("could not find class for 'TypedElement'", typedElementClassifier != null);
        Property type = typedElementClassifier.findProperty("type");
        assertTrue("No ownedAttribute 'type' found for class 'TypedElement'", type != null);        
    }

    public void testNamedElement() throws Exception {    
        log.info("testTypedElement");
        Class_ namedElementClassifier = (Class_)Repository.INSTANCE.findClassifier(NamedElement.class.getSimpleName());
        assertTrue("could not find class for 'NamedElement'", namedElementClassifier != null);
        Property name = namedElementClassifier.findProperty("name");
        assertTrue("No ownedAttribute 'name' found for class 'NamedElement'", name != null);
        Property visibility = namedElementClassifier.findProperty("visibility");
        assertTrue("No ownedAttribute 'visibility' found for class 'NamedElement'", visibility != null);
        
    }
    
    public void testFeature() throws Exception {    
        log.info("testFeature");
        Class_ featureClassifier = (Class_)Repository.INSTANCE.findClassifier(Feature.class.getSimpleName());
        assertTrue("could not find class for 'Feature'", featureClassifier != null);
        Property isLeaf = featureClassifier.findProperty("isLeaf");
        assertTrue("No ownedAttribute 'isLeaf' found for class 'Feature'", isLeaf != null);
    }
    
    public void testActivityParameterNode() throws Exception {    
        log.info("testActivityParameterNode");
        Class_ nodeClassifier = (Class_)Repository.INSTANCE.findClassifier(ActivityParameterNode.class.getSimpleName());
        assertTrue("could not find class for 'ActivityParameterNode'", nodeClassifier != null);
        Property type = nodeClassifier.findProperty("type");
        assertTrue("No ownedAttribute 'type' found for class 'ActivityParameterNode'", type != null);
        
        List<Property> props = nodeClassifier.getNamedProperties();
        for (Property prop : props) {
        	Property opposite = prop.getOpposite();
            if (opposite != null)
            	log.info(prop.getClass_().getName() + "." + prop.getName() + "->" 
            			+ opposite.getClass_().getName() + "." + opposite.getName());
        }
        
        // upperBound not in fUML
        //Property upperBound = nodeClassifier.findProperty("upperBound");
        //assertTrue("No ownedAttribute 'upperBound' found for class 'ActivityParameterNode'", upperBound != null);
    }
    
    public void testClass() throws Exception {    
        log.info("testClass");
        Class_ classifier = (Class_)Repository.INSTANCE.findClassifier("Class");
        assertTrue("could not find class for 'Class'", classifier != null);
        Property isAbstract = classifier.findProperty("isAbstract");
        assertTrue("No ownedAttribute 'isAbstract' found for class 'Class'", isAbstract != null);
        assertTrue("Expected 'Class.isAbstract' as singular ownedAttribute", 
        		isAbstract.isSingular());
        assertTrue("Expected 'Class.isAbstract' as required ownedAttribute", 
        		isAbstract.isRequired());
        assertTrue("Expected default value for 'Class.isAbstract'", 
        		isAbstract.hasPropertyDefaultValue());
        Property generalizationProp = classifier.findProperty("generalization");
        assertTrue("No ownedAttribute 'generalization' found for class 'Class'", generalizationProp != null);
    }

    public void testProperty() throws Exception {    
        log.info("testProperty");
        Class_ propertyClassifier = (Class_)Repository.INSTANCE.findClassifier("Property");
        assertTrue("could not find class for 'Property'", propertyClassifier != null);
        logProperties((Class_)propertyClassifier);
        Property defaultValue = propertyClassifier.findProperty("defaultValue");
        assertTrue("No ownedAttribute 'defaultValue' found for class 'Property'", defaultValue != null);
    }
    
    private void logProperties(Class_ classifier) {
        List<Property> props = classifier.getNamedProperties();
        for (Property prop : props) {
        	log.info(classifier.getName() + ": " + prop.getClass_().getName() + "." + prop.getName() 
        			+ " (required=" + String.valueOf(
        					prop.isRequired()) 
        				+ ")");
        	Property opposite = prop.getOpposite();
            if (opposite != null)
            	log.info(classifier.getName() + ": opposite: " + prop.getClass_().getName() + "." + prop.getName() + "->" 
            			+ opposite.getClass_().getName() + "." + opposite.getName());
        }      
    	
    }
    
    public void testObjectNode() throws Exception {    
        log.info("testObjectNode");
        Class_ objectNodeClassifier = (Class_)Repository.INSTANCE.findClassifier(ObjectNode.class.getSimpleName());
        assertTrue("could not find classifier 'ObjectNode'", objectNodeClassifier != null);
        /*
        Property orderingProp = ModelAccess.INSTANCE.findAttribute((Class_)objectNodeClassifier, "ordering");
        assertTrue("No ownedAttribute 'ordering' found for class 'ObjectNode'", orderingProp != null);
        assertTrue("Expected 'ObjectNode.ordering' as required property",
            ModelAccess.INSTANCE.isRequired((Class_)objectNodeClassifier, orderingProp));
        String dflt = ModelAccess.INSTANCE.getAttributeDefault(orderingProp);
        assertTrue("no default found for 'ObjectNode.ordering'", 
                dflt != null);
        assertTrue("expected 'FIFO' as default for 'ObjectNode.ordering'", 
                "FIFO".equals(dflt));
        */
    }
    
    public void testExpansionRegion() throws Exception {    
        log.info("testExpansionRegion");
        Class_ expansionRegionClassifier = (Class_)Repository.INSTANCE.findClassifier(ExpansionRegion.class.getSimpleName());
        assertTrue("could not find classifier 'ExpansionRegion'", expansionRegionClassifier != null);
        Property modeProp = expansionRegionClassifier.findProperty("mode");
        assertTrue("No ownedAttribute 'mode' found for class 'ExpansionRegion'", modeProp != null);
        String dflt = modeProp.getPropertyDefault();
        
        assertTrue("Expected default for 'mode' property for class 'ExpansionRegion'", 
                dflt != null);        
    }

    public void testPackage() throws Exception {    
        log.info("testPackage");
        Class_ pkg = (Class_)Repository.INSTANCE.findClassifier("Package");
        assertTrue("could not find class for 'Package'", pkg != null);

        Property nestingPackage = pkg.findProperty("nestingPackage");
        assertTrue("No ownedAttribute 'nestingPackage' found for class 'Package'", nestingPackage != null);
        Property nestingPackageOpposite = nestingPackage.getOpposite();
        assertTrue(nestingPackageOpposite != null);
        assertTrue(nestingPackageOpposite.getClass_().getName().equals("Package"));
        assertTrue(nestingPackageOpposite.getName().equals("nestedPackage"));
        
        List<Property> props = pkg.getNamedProperties();
        for (Property prop : props) {
        	Property opposite = prop.getOpposite();
            if (opposite != null)
            	log.info(prop.getClass_().getName() + "." + prop.getName() + "->" 
            			+ opposite.getClass_().getName() + "." + opposite.getName());
        }
    }
    
    public void testStereotype() throws Exception {    
        log.info("testStereotype");
        Class_ stereotype = (Class_)Repository.INSTANCE.findClassifier("Stereotype");
        assertTrue("could not find class for 'Stereotype'", stereotype != null);
        Property profile = stereotype.findProperty("profile");
        assertTrue("No ownedAttribute 'profile' found for class 'Stereotype'", profile != null);
        assertTrue("Expected 'Stereotype.profile' as derived ownedAttribute", 
        		profile.isDerived());        
    }
    
}