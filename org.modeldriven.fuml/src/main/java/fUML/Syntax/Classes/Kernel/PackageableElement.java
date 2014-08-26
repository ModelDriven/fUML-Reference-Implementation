
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2013 Ivar Jacobson International SA
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class PackageableElement extends
		fUML.Syntax.Classes.Kernel.NamedElement {

	public PackageableElement() {
		super.setVisibility(VisibilityKind.public_);
	}

} // PackageableElement
