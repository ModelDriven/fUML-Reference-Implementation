package org.modeldriven.fuml.repository.ext;



public class Profile extends fUML.Syntax.Classes.Kernel.Package {
	public fUML.Syntax.Classes.Kernel.ElementImportList metaclassReference = new fUML.Syntax.Classes.Kernel.ElementImportList();

	public void addMetaclassReference(
			fUML.Syntax.Classes.Kernel.ElementImport elementImport) {

		this.metaclassReference.addValue(elementImport);
	} // addMetaclassReference


} // Profile
