
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

public abstract class RedefinableElement extends
		fUML.Syntax.Classes.Kernel.NamedElement {

	public boolean isLeaf = false;
	public fUML.Syntax.Classes.Kernel.RedefinableElementList redefinedElement = new fUML.Syntax.Classes.Kernel.RedefinableElementList();
	public fUML.Syntax.Classes.Kernel.ClassifierList redefinitionContext = new fUML.Syntax.Classes.Kernel.ClassifierList();

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	} // setIsLeaf

	protected void addRedefinedElement(
			fUML.Syntax.Classes.Kernel.RedefinableElement redefinedElement) {
		this.redefinedElement.addValue(redefinedElement);
	} // addRedefinedElement

	protected void addRedefinitionContext(
			fUML.Syntax.Classes.Kernel.Classifier redefinitionContext) {
		this.redefinitionContext.addValue(redefinitionContext);
	} // addRedefinitionContext

} // RedefinableElement
