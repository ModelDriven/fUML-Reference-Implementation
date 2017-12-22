
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

package fuml.syntax.activities;

public class ActivityParameterNode extends
		fuml.syntax.activities.ObjectNode {

	public fuml.syntax.classification.Parameter parameter = null;

	public void setParameter(fuml.syntax.classification.Parameter parameter) {
		this.parameter = parameter;
	} // setParameter

} // ActivityParameterNode
