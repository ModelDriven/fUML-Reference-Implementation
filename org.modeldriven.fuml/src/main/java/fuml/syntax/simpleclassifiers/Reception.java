
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

package fuml.syntax.simpleclassifiers;

public class Reception extends fuml.syntax.classification.BehavioralFeature {

	public fuml.syntax.simpleclassifiers.Signal signal = null;

	public void setSignal(
			fuml.syntax.simpleclassifiers.Signal signal) {
		this.signal = signal;
	} // setSignal

} // Reception
