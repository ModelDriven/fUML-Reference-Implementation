package org.modeldriven.fuml.repository;


public interface RepositoryMapping {

	public Element mapElementById(fUML.Syntax.Classes.Kernel.Element element, RepositoryArtifact artifact);
	public NamedElement mapElementByName(fUML.Syntax.Classes.Kernel.NamedElement element, RepositoryArtifact artifact);
	public Package mapPackage(fUML.Syntax.Classes.Kernel.Package pkg, String currentPackageName, RepositoryArtifact artifact);
	public void mapPackageMerge(fUML.Syntax.Classes.Kernel.Package pkg, String sourcePackageXmiId);
	public Classifier mapClassifier(fUML.Syntax.Classes.Kernel.Classifier classifier, String currentPackageName, RepositoryArtifact artifact);
	public Class_ mapClass(fUML.Syntax.Classes.Kernel.Class_ clss, String currentPackageName, RepositoryArtifact artifact);
	public Stereotype mapStereotype(org.modeldriven.fuml.repository.ext.Stereotype stereotype, String currentPackageName, RepositoryArtifact artifact);
	public Property mapProperty(fUML.Syntax.Classes.Kernel.Classifier clss, fUML.Syntax.Classes.Kernel.Property proprty, RepositoryArtifact artifact);
	public Classifier mapPrimitiveType(fUML.Syntax.Classes.Kernel.PrimitiveType type, String currentPackageName, RepositoryArtifact artifact);
	public Enumeration mapEnumeration(fUML.Syntax.Classes.Kernel.Enumeration e, String currentPackageName, RepositoryArtifact artifact);
	public Enumeration mapEnumerationExternal(fUML.Syntax.Classes.Kernel.Enumeration e, String currentPackageName, RepositoryArtifact artifact);
	public EnumerationLiteral mapEnumerationLiteral(fUML.Syntax.Classes.Kernel.EnumerationLiteral literal, String currentPackageName, RepositoryArtifact artifact);
	public Association mapAssociation(fUML.Syntax.Classes.Kernel.Association assoc, String currentPackageName, RepositoryArtifact artifact);
	public DataType mapDataType(fUML.Syntax.Classes.Kernel.DataType t, String currentPackageName, RepositoryArtifact artifact);
}
