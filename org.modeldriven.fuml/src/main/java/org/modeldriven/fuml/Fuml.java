/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.io.ArtifactLoader;
import org.modeldriven.fuml.io.IncrementalArtifactLoader;
import org.modeldriven.fuml.io.FileArtifact;
import org.modeldriven.fuml.io.ResourceArtifact;

import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class Fuml {
    private static Log log = LogFactory.getLog(Fuml.class);

    @SuppressWarnings("unused")
	private Fuml() {}
    
    /**
     * Loads the given model-file and executes the behavior 
     * found within the given model-file. If more than one top-level behavior
     * is found within the given model-file, an error is thrown.
     * @param file the model-file
     * for the given model-file
     */
    public Fuml(File file) {
        execute(file, file.getName(), null);
    }

    /**
     * Loads the given model-file and executes the given target behavior 
     * found within the given model-file
     * @param file the model-file to load
     * @param uri the runtime-environment specific file or document URI 
     * for the given model-file. If null the name of the file is used.
     * @param target the name for the given target behavior to execute 
     */
    public Fuml(File file, String uri, String target) {
        execute(file, uri != null ? uri : file.getName(), target);
    }
    
    /**
     * Loads the given model-file and executes the given target behavior 
     * found within the given model-file
     * @param file the model-file to load
     * @param target the name for the given target behavior to execute 
     */
    public Fuml(File file, String target) {
        execute(file, file.getName(), target);
    }
    
    /**
     * Executes the given previously loaded target behavior.
     * @param namespaceURI the runtime-environment specific namespace URI 
     * for the given target behavior to execute. 
     * @param target the name for the given target behavior to execute 
     */
    public Fuml(String namespaceURI, String target) {
        execute(namespaceURI, target);
    }

    /**
     * Reads and loads the given file as a model artifact.
     * @param file the model-file to load
     * @param uri the runtime-environment specific document URI
     */
    public static void load(File file, String uri) {
        try {
        	log.info("loading artifact, " + file.getName());
        	ArtifactLoader reader = new ArtifactLoader();
        	reader.read(new FileArtifact(file, uri));
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }

    /**
     * Reads and loads the given file as a model artifact.
     * @param fileArtifact the FileArtifact to load
     */
    public static void load(FileArtifact fileArtifact) {
        try {
        	log.info("loading artifact, " + fileArtifact.getURN());
        	ArtifactLoader reader = new ArtifactLoader();
        	reader.read(fileArtifact);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }   
    
    /**
     * Reads and loads the given file as a model artifact using an incremental
     * loading scheme.
     * @param file the file to load
     * @param uri the runtime-environment specific document URI
     */
    public static void loadIncrementally(File file, String uri) {
        try {
        	log.info("reading artifact, " + file.getName());
        	IncrementalArtifactLoader reader = new IncrementalArtifactLoader();
        	reader.read(new FileArtifact(file, uri));
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }

    /**
     * Reads and loads the given resource as a model artifact.
     * @param file the file to load
     */
    public static void load(ResourceArtifact artifact) {
        try {
        	log.info("loading artifact, " + artifact.getURN());
        	ArtifactLoader reader = new ArtifactLoader();
        	reader.read(artifact);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }   
    
    /**
     * Reads and loads the given resource as a model artifact using an incremental
     * loading scheme.
     * @param file the file to load
     */
    public static void loadIncrementally(ResourceArtifact artifact) {
        try {
        	log.info("reading artifact, " + artifact.getURN());
        	IncrementalArtifactLoader reader = new IncrementalArtifactLoader();
        	reader.read(artifact);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }
    
    protected void execute(String uri, String target) {
        try {
            Environment environment = Environment.getInstance();
            Behavior behavior = environment.findBehavior(target);
            if (behavior == null)
                throw new FumlException("unknown behavior name, '" + target + "'");
            ExecutionEnvironment execution = new ExecutionEnvironment(environment);
            log.info("executing behavior: " + behavior.name);
            execution.execute(behavior);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }

    protected void execute(File file, String uri, String target) {
        try {
        	log.info("loading artifact, " + file.getName());
        	IncrementalArtifactLoader reader = new IncrementalArtifactLoader();
        	reader.read(new FileArtifact(file, uri));

            Environment environment = Environment.getInstance();
            Behavior behavior = null;

            if (target != null) {
                behavior = environment.findBehavior(target);
                if (behavior == null)
                    throw new FumlException("unknown behavior name, '" + target + "'");
            } else {
                String[] names = environment.getBehaviorNames();
                if (names.length > 1) {
                	StringBuffer namesBuf = new StringBuffer();
                	namesBuf.append("[");
                	for (int i = 0; i < names.length; i++) {
                		if (i > 0)
                			namesBuf.append(", ");
                		namesBuf.append("'");
                		namesBuf.append(names[i]);
                		namesBuf.append("'");
                		if (i > 10)
                			namesBuf.append("...");
                	}	
                	namesBuf.append("]");
                    throw new FumlException("ambiguous execution target - file '" + file.getName()
                            + "' contains " + String.valueOf(names.length)
                            + " behaviors to execute "
                            + namesBuf.toString() + " - please specify a behavior");
                }
                else if (names.length == 0)
                    throw new FumlException("invalid execution target - file '" + file.getName()
                            + "' contains " + String.valueOf(names.length) + " behaviors");

                behavior = environment.findBehavior(names[0]);
                if (behavior == null)
                    throw new FumlException("unknown behavior name, '" + names[0] + "'");
            }
            ExecutionEnvironment execution = new ExecutionEnvironment(environment);
            log.info("executing behavior: " + behavior.name);
            execution.execute(behavior);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new FumlException(t);
        }
    }
    
    protected static void printUsage() {
        log.info("====================================================================");
        log.info("USAGE: fuml <model-file-name> [<behavior-name> <behavior-name> <behavior-name> <...>]");
        log.info("====================================================================");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        File modelFile = new File(args[0]);
        if (args.length == 1) {
        	new Fuml(modelFile);
        } 
        else {
            Fuml fuml = new Fuml(modelFile, args[1]);
            for (int i = 2; i < args.length; i++)
            	fuml.execute(modelFile.getName(), args[i]);
        }
    }
}
