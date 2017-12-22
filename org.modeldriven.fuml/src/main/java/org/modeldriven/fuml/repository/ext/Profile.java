package org.modeldriven.fuml.repository.ext;



public class Profile extends fuml.syntax.packages.Package {
	public fuml.syntax.commonstructure.ElementImportList metaclassReference = new fuml.syntax.commonstructure.ElementImportList();

	public void addMetaclassReference(
			fuml.syntax.commonstructure.ElementImport elementImport) {

		this.metaclassReference.addValue(elementImport);
	} // addMetaclassReference


} // Profile
