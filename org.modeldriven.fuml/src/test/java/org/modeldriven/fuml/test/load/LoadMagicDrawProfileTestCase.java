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

import org.junit.After;
import org.junit.Before;
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
import org.modeldriven.fuml.test.load.profile.EnumerationConstraint;
import org.modeldriven.fuml.test.load.profile.ValueConstraint;

/**
 * Loads a Magic Draw (mdxml) UML profile example on test setup, then loads a document annotated with the
 * profile and tests for the existence of various stereotypes and their values tied to specific
 * properties and other elements.
 */
public class LoadMagicDrawProfileTestCase extends FUMLTest {
	private static Log log = LogFactory.getLog(LoadMagicDrawProfileTestCase.class);

	private static Environment environment; // JUnit creates a new test class
											// for every test!
	String FILE_URN = "TestProfileApplication_File";
	String NAMESPACE_URI = "test/mdxml/TestProfileApplication_File";

	public static Test suite() {
		return FUMLTestSetup.newTestSetup(LoadMagicDrawProfileTestCase.class);
	}

	public void setUp() throws Exception {

		if (environment == null) {
			String filename = "./target/test-classes/mdxml/TestProfile.mdxml";
			File file = new File(filename);
			assertTrue("file '" + filename + "' does not exist", file.exists());
			FileInputStream stream = new FileInputStream(file);
			String PROFILE_NAMESPACE_URI = "http://www.magicdraw.com/schemas/Test_Profile.xmi";
			String PROFILE_FILE_URN = "Test_Profile.mdxml";
			ResourceArtifact artifact = new ResourceArtifact(PROFILE_FILE_URN,
					PROFILE_NAMESPACE_URI, stream);
			Fuml.load(artifact);
			environment = Environment.getInstance();
		}
	}

	@org.junit.Test
	public void testLoadProfileApplication() throws Exception {
		log.info("testLoadProfileApplication");
		String filename = "./target/test-classes/mdxml/TestProfileApplication.mdxml";
		File file = new File(filename);
		assertTrue("file '" + filename + "' does not exist", file.exists());
		FileInputStream stream = new FileInputStream(file);
		ResourceArtifact artifact = new ResourceArtifact(FILE_URN,
				NAMESPACE_URI, stream);
		Fuml.load(artifact);	

		// test package qualified name
		Classifier nodeClassifier = Repository.INSTANCE.findClassifier("model.org.modeldriven.test.data.Node");
		assertTrue(nodeClassifier != null);
		assertTrue(nodeClassifier instanceof Class_);

		// test uri qualified name
		nodeClassifier = Repository.INSTANCE.findClassifier(
				NAMESPACE_URI + "#" + "Node");
		assertTrue(nodeClassifier != null);
		assertTrue(nodeClassifier instanceof Class_);
		
		
		Class_ nodeClass = (Class_)nodeClassifier;
		Property stringsFieldProp = nodeClass.getProperty("stringsField");
		assertTrue(stringsFieldProp != null);
		assertTrue(hasStereotype(stringsFieldProp, ValueConstraint.class));
		ValueConstraint valueConstraint = (ValueConstraint)getStereotype(stringsFieldProp, 
				ValueConstraint.class).getDelegate();
		assertTrue(Integer.parseInt(valueConstraint.getMaxLength()) == 30);
		
		Property stringFieldProp = nodeClass.getProperty("stringField");		
		assertTrue(hasStereotype(stringFieldProp, EnumerationConstraint.class));
		EnumerationConstraint enumConstraint = (EnumerationConstraint)getStereotype(stringFieldProp, 
				EnumerationConstraint.class).getDelegate();
		fuml.syntax.simpleclassifiers.Enumeration e = enumConstraint.getValue();		
		Enumeration repoEnum = (Enumeration)Repository.INSTANCE.findElementById(e.getXmiId());
		assertTrue(repoEnum != null);
		assertTrue("TestValues".equals(repoEnum.getName()));
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