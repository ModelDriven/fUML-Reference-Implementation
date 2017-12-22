
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

package fuml.syntax.classification;

public abstract class RedefinableElement extends
		fuml.syntax.commonstructure.NamedElement {

	public boolean isLeaf = false;
	public fuml.syntax.classification.RedefinableElementList redefinedElement = new fuml.syntax.classification.RedefinableElementList();
	public fuml.syntax.classification.ClassifierList redefinitionContext = new fuml.syntax.classification.ClassifierList();

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	} // setIsLeaf

	protected void addRedefinedElement(
			fuml.syntax.classification.RedefinableElement redefinedElement) {
		this.redefinedElement.addValue(redefinedElement);
	} // addRedefinedElement

	protected void addRedefinitionContext(
			fuml.syntax.classification.Classifier redefinitionContext) {
		this.redefinitionContext.addValue(redefinitionContext);
	} // addRedefinitionContext

} // RedefinableElement
