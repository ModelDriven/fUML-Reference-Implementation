/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * Modifications:
 * Copyright 2009 Data Access Technologies, Inc.
 * Copyright 2013 Ivar Jacobson International SA
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *   IJI
 *
 */
package org.modeldriven.fuml.test.load;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.Enumeration;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.Stereotype;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;
import org.modeldriven.fuml.test.load.profile.Alias;
import org.modeldriven.fuml.test.load.profile.EnumerationConstraint;

/**
 * Loads an Eclipse Papyrus UML profile example on test setup, then loads a document annotated with the
 * profile and tests for the existence of various stereotypes and their values tied to specific
 * properties and other elements.
 */
public class LoadPapyrusProfileTestCase extends FUMLTest {
	private static Log log = LogFactory.getLog(LoadPapyrusProfileTestCase.class);

	private static Environment environment; // JUnit creates a new test class
											// for every test!
	String FILE_URN = "TestPapyrusProfileApplication_File";
	String NAMESPACE_URI = "test/uml/TestPapyrusProfileApplication_File";

	public static Test suite() {
		return FUMLTestSetup.newTestSetup(LoadPapyrusProfileTestCase.class);
	}

	public void setUp() throws Exception {

		if (environment == null) {
			String filename = "./target/test-classes/uml/TestPapyrus.profile.uml";
			File file = new File(filename);
			assertTrue("file '" + filename + "' does not exist", file.exists());
			FileInputStream stream = new FileInputStream(file);
			String PROFILE_NAMESPACE_URI = "http:///schemas/TestProfile/_Cvni8DqeEeSIqNFH5qbSSw/28";
			String PROFILE_FILE_URN = "Test.profile.uml";
			ResourceArtifact artifact = new ResourceArtifact(PROFILE_FILE_URN,
					PROFILE_NAMESPACE_URI, stream);
			Fuml.load(artifact);

			filename = "./target/test-classes/uml/TestPapyrusDataTypes.uml";
			file = new File(filename);
			assertTrue("file '" + filename + "' does not exist", file.exists());
			stream = new FileInputStream(file);
			PROFILE_NAMESPACE_URI = "http:///schemas/TestDataTypes";
			PROFILE_FILE_URN = "TestDataTypes.uml";
			artifact = new ResourceArtifact(PROFILE_FILE_URN,
					PROFILE_NAMESPACE_URI, stream);
			Fuml.load(artifact);
			
			environment = Environment.getInstance();
		}
	}
	
	 
	@org.junit.Test
	public void testLoadProfileApplication() throws Exception {
		
		 
		log.info("testLoadPapyrusProfileApplication");
		String filename = "./target/test-classes/uml/TestPapyrusProfileApplication.uml";
		File file = new File(filename);
		assertTrue("file '" + filename + "' does not exist", file.exists());
		FileInputStream stream = new FileInputStream(file);
		ResourceArtifact artifact = new ResourceArtifact(FILE_URN,
				NAMESPACE_URI, stream);
		Fuml.load(artifact);	

		Classifier personClassifier = Repository.INSTANCE.findClassifier(
				NAMESPACE_URI + "#" + "Person");
		assertTrue(personClassifier != null);
		assertTrue(personClassifier instanceof Class_);
		Class_ personClass = (Class_)personClassifier;
		Property firstNameProp = personClass.getProperty("firstName");
		assertTrue(firstNameProp != null);
		assertTrue(hasStereotype(firstNameProp, Alias.class));
		Alias alias = (Alias)getStereotype(firstNameProp, 
				Alias.class).getDelegate();
		assertTrue("FRST_NM".equals(alias.getPhysicalName()));
		
		Classifier orgClassifier = Repository.INSTANCE.findClassifier(
				NAMESPACE_URI + "#" + "Org");
		assertTrue(orgClassifier != null);
		assertTrue(orgClassifier instanceof Class_);
		Class_ orgClass = (Class_)orgClassifier;
		Property dunsProp = orgClass.getProperty("dunsNumber");
		assertTrue(dunsProp != null);
		assertTrue(hasStereotype(dunsProp, EnumerationConstraint.class));
		EnumerationConstraint enumConstraint = (EnumerationConstraint)getStereotype(dunsProp, 
				EnumerationConstraint.class).getDelegate();
		fuml.syntax.simpleclassifiers.Enumeration e = enumConstraint.getValue();		
		Enumeration repoEnum = (Enumeration)Repository.INSTANCE.findElementById(e.getXmiId());
		assertTrue(repoEnum != null);
		assertTrue("DUNSType".equals(repoEnum.getName()));
		assertTrue(repoEnum.getOwnedLiteral() != null);
		
		 
	}	
	 
	 
	private boolean hasStereotype(Element elem, Class<?> stereotypeClass) {
		List<Stereotype> list = Repository.INSTANCE.getStereotypes(elem);
		for (Stereotype s : list) {
			if (stereotypeClass.isAssignableFrom(s.getDelegate().getClass()))
			    return true;
		}
		return false;
	}
	
	private Stereotype getStereotype(Element elem, Class<?> stereotypeClass) {
		List<Stereotype> list = Repository.INSTANCE.getStereotypes(elem);
		for (Stereotype s : list) {
			if (stereotypeClass.isAssignableFrom(s.getDelegate().getClass()))
			    return s;
		}
		throw new IllegalArgumentException("no stereotype class found for, " + stereotypeClass.getName());
	}
	
	
	

}