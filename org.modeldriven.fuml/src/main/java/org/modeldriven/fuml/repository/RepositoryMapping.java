package org.modeldriven.fuml.repository;

import org.modeldriven.fuml.repository.ext.Stereotype;

import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Package;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Property;

public interface RepositoryMapping {

	public void mapElementById(Element element, RepositoryArtifact artifact);
	public void mapElementByName(NamedElement element, RepositoryArtifact artifact);
	public void mapPackage(Package pkg, String currentPackageName, RepositoryArtifact artifact);
	public void mapPackageMerge(Package pkg, String sourcePackageXmiId);
	public void mapClassifier(Classifier classifier, String currentPackageName, RepositoryArtifact artifact);
	public void mapClass(Class_ clss, String currentPackageName, RepositoryArtifact artifact);
	public void mapStereotype(Stereotype stereotype, String currentPackageName, RepositoryArtifact artifact);
	public void mapProperty(Classifier clss, Property proprty, RepositoryArtifact artifact);
	public void mapPrimitiveType(PrimitiveType type, String currentPackageName, RepositoryArtifact artifact);
	public void mapEnumeration(Enumeration e, String currentPackageName, RepositoryArtifact artifact);
	public void mapEnumerationLiteral(EnumerationLiteral literal, String currentPackageName, RepositoryArtifact artifact);
	public void mapAssociation(Association assoc, String currentPackageName, RepositoryArtifact artifact);
	public void mapDataType(DataType t, String currentPackageName, RepositoryArtifact artifact);
}
