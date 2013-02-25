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
        log.info("initializing...");
        config = FumlConfiguration.getInstance().getConfig().getLibraryConfiguration();
        if (config != null && config.getLibraryImport() != null) {
	        Iterator<LibraryImport> libraryImports = config.getLibraryImport().iterator();
	        while (libraryImports.hasNext()) {
	            load(libraryImports.next());
	        }
        }
    }

    public static Library getInstance() throws LibraryException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() {
        if (instance == null)
            instance = new Library();
    }

    @SuppressWarnings("unchecked")
    private void load(LibraryImport libraryImport) {
        log.info("loading library artifact, " + libraryImport.getName());
        InputStream stream = Library.class.getResourceAsStream(libraryImport.getName());
        if (stream == null)
        	stream = Library.class.getClassLoader().getResourceAsStream(libraryImport.getName());
        if (stream == null)
            throw new LibraryException("cannot find resource '" + libraryImport.getName() + "'");

        
        ResourceArtifact artifact = new ResourceArtifact(libraryImport.getName(),
        		libraryImport.getUrn(),
        		stream);
        
        ArtifactLoader reader = new ArtifactLoader();
        // this is a library which is itself used to
        // validate and assemble external references
        reader.setValidateExternalReferences(false);
        reader.setAssembleExternalReferences(false);
        
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
