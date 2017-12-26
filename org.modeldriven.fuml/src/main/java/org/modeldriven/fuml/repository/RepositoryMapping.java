package org.modeldriven.fuml.repository;

import org.modeldriven.fuml.repository.ext.Stereotype;

import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.Property;
import fuml.syntax.commonstructure.Element;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.packages.Package;
import fuml.syntax.simpleclassifiers.DataType;
import fuml.syntax.simpleclassifiers.Enumeration;
import fuml.syntax.simpleclassifiers.EnumerationLiteral;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import fuml.syntax.structuredclassifiers.Association;
import fuml.syntax.structuredclassifiers.Class_;

public interface RepositoryMapping {

	public void mapElementById(Element element, RepositoryArtifact artifact);
	public void mapElementByName(NamedElement element, RepositoryArtifact artifact);
	public void mapPackage(Package pkg, String currentPackageName, RepositoryArtifact artifact);
	public void mapClassifier(Classifier classifier, String currentPackageName, RepositoryArtifact artifact);
	public void mapClass(Class_ clss, String currentPackageName, RepositoryArtifact artifact);
	public void mapStereotype(Stereotype stereotype, String currentPackageName, RepositoryArtifact artifact);
	public void mapProperty(Classifier clss, Property proprty, RepositoryArtifact artifact);
	public void mapPrimitiveType(PrimitiveType type, String currentPackageName, RepositoryArtifact artifact);
	public void mapEnumeration(Enumeration e, String currentPackageName, RepositoryArtifact artifact);
	public void mapEnumerationExternal(Enumeration e, String currentPackageName, RepositoryArtifact artifact);
	public void mapEnumerationLiteral(EnumerationLiteral literal, String currentPackageName, RepositoryArtifact artifact);
	public void mapAssociation(Association assoc, String currentPackageName, RepositoryArtifact artifact);
	public void mapDataType(DataType t, String currentPackageName, RepositoryArtifact artifact);
}
