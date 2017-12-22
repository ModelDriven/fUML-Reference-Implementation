
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

package fuml.syntax.actions;

public class DestroyObjectAction extends
		fuml.syntax.actions.Action {

	public boolean isDestroyLinks = false;
	public boolean isDestroyOwnedObjects = false;
	public fuml.syntax.actions.InputPin target = null;

	public void setIsDestroyLinks(boolean isDestroyLinks) {
		this.isDestroyLinks = isDestroyLinks;
	} // setIsDestroyLinks

	public void setIsDestroyOwnedObjects(boolean isDestroyOwnedObjects) {
		this.isDestroyOwnedObjects = isDestroyOwnedObjects;
	} // setIsDestroyOwnedObjects

	public void setTarget(fuml.syntax.actions.InputPin target) {
		super.addInput(target);
		this.target = target;
	} // setTarget

} // DestroyObjectAction
