
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

public class TypedElement extends fuml.syntax.commonstructure.NamedElement {

	public fuml.syntax.commonstructure.Type type = null;

	public void setType(fuml.syntax.commonstructure.Type type) {
		this.type = type;
	} // setType

} // TypedElement
