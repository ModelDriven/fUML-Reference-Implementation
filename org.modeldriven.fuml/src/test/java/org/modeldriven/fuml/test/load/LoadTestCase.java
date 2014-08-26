package org.modeldriven.fuml.test.load;



import java.io.File;
import java.util.List;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.environment.InvalidExecutionTargetException;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class LoadTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(LoadTestCase.class);
    
    private static Environment environment; // JUnit creates a new test class for every test! 
    private static String FILE_URI;
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LoadTestCase.class);
    }
    
    public void setUp() throws Exception {
    	
    	if (environment == null) {
	    	String filename = "./target/test-classes/mdxml/fUML-Load.mdxml";
	        File file = new File(filename);
	        assertTrue("file '" + filename + "' does not exist", file.exists());
	        FILE_URI = "test/mdxml/fUML-Load.mdxml";
	        Fuml.loadIncrementally(file, FILE_URI);
	        environment = Environment.getInstance();
    	}   	
    }

    public void testLoadOpaqueBehavior() throws Exception {    
        log.info("testLoadOpaqueBehavior");
        String qualifiedName = FILE_URI + "#" + "LoadBehaviorTest";
        Class_ clss = (Class_)Repository.INSTANCE.findClassifier(qualifiedName);
        assertTrue("could not find class for, " + qualifiedName, clss != null);
        
        List<org.modeldriven.fuml.repository.model.OpaqueBehavior> behaviors = clss.getOpaqueBehaviors();
        assertTrue("expected behaviors for class 'Package'", behaviors != null && behaviors.size() > 0);
        for (org.modeldriven.fuml.repository.model.OpaqueBehavior behavior : behaviors) {
        	assertTrue("no body for behavior, " + behavior.getName(), 
        		behavior.getBody() != null && behavior.getBody().length() >0);
        }
        
    }
}