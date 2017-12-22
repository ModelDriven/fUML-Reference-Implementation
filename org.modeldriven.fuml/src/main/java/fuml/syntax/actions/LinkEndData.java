
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

public class LinkEndData extends fuml.syntax.commonstructure.Element {

	public fuml.syntax.actions.InputPin value = null;
	public fuml.syntax.classification.Property end = null;

	public void setEnd(fuml.syntax.classification.Property end) {
		this.end = end;
	} // setEnd

	public void setValue(fuml.syntax.actions.InputPin value) {
		this.value = value;
	} // setValue

} // LinkEndData
