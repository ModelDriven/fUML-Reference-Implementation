
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

public class LinkEndDestructionData extends
		fuml.syntax.actions.LinkEndData {

	public boolean isDestroyDuplicates = false;
	public fuml.syntax.actions.InputPin destroyAt = null;

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.isDestroyDuplicates = isDestroyDuplicates;
	} // setIsDestroyDuplicates

	public void setDestroyAt(fuml.syntax.actions.InputPin destroyAt) {
		this.destroyAt = destroyAt;
	} // setDestroyAt

} // LinkEndDestructionData
