
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

package fuml.syntax.commonstructure;

public abstract class Element extends org.modeldriven.fuml.FumlObject {

	public fuml.syntax.commonstructure.ElementList ownedElement = new fuml.syntax.commonstructure.ElementList();
	public fuml.syntax.commonstructure.Element owner = null;
	public fuml.syntax.commonstructure.CommentList ownedComment = new fuml.syntax.commonstructure.CommentList();

	protected void addOwnedElement(
			fuml.syntax.commonstructure.Element ownedElement) {
		this.ownedElement.add(ownedElement);
		ownedElement.owner = this;

	} // addOwnedElement

} // Element
