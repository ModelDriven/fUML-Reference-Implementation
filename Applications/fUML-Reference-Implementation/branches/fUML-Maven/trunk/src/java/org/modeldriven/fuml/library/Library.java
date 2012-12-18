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
package org.modeldriven.fuml.library;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.config.LibraryConfiguration;
import org.modeldriven.fuml.config.LibraryImport;
import org.modeldriven.fuml.io.ArtifactLoader;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.Repository;

import fUML.Syntax.Classes.Kernel.NamedElement;

public class Library {

    private static Log log = LogFactory.getLog(Library.class);
    private static Library instance = null;

    private LibraryConfiguration config;

    private Library() {
    }
    
    public static Library getInstance() throws LibraryException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() {
    	if (instance == null) {
    		// NOTE: Separating initialization from construction avoids the
    		// possibility of an infinite loop if an attempt is made to 
    		// access the library instance during initialization.
    		instance = new Library();
    		instance.initialize();
    	}
    }

    private void initialize() {
        log.info("initializing...");
        config = FumlConfiguration.getInstance().getConfig().getLibraryConfiguration();
        if (config != null && config.getLibraryImport() != null) {
	        Iterator<LibraryImport> libraryImports = config.getLibraryImport().iterator();
	        while (libraryImports.hasNext()) {
	            load(libraryImports.next());
	        }
        }
    }

    @SuppressWarnings("unchecked")
    private void load(LibraryImport libraryImport) {
        log.info("loading library artifact, " + libraryImport.getName());
        InputStream stream = Library.class.getResourceAsStream(libraryImport.getName());
        if (stream == null)
            throw new LibraryException("cannot find resource '" + libraryImport.getName() + "'");

        
        ResourceArtifact artifact = new ResourceArtifact(libraryImport.getName(),
        		libraryImport.getUrn(),
        		stream);
        
        ArtifactLoader reader = new ArtifactLoader();
        reader.read(artifact);
        log.info("completed library artifact, " + libraryImport.getName());
    }

    public NamedElement lookup(String id) {
    	
    	Element elem = Repository.INSTANCE.findElementById(id);
    	if (elem != null)   	
    	    return (NamedElement)elem.getDelegate(); 
    	else
    		return null;
    }

}
