
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class Element extends org.modeldriven.fuml.FumlObject {

	public fUML.Syntax.Classes.Kernel.ElementList ownedElement = new fUML.Syntax.Classes.Kernel.ElementList();
	public fUML.Syntax.Classes.Kernel.Element owner = null;
	public fUML.Syntax.Classes.Kernel.CommentList ownedComment = new fUML.Syntax.Classes.Kernel.CommentList();

	protected void addOwnedElement(
			fUML.Syntax.Classes.Kernel.Element ownedElement) {
		this.ownedElement.add(ownedElement);
		ownedElement.owner = this;

	} // addOwnedElement

} // Element
