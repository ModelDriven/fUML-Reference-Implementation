
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

public class Generalization extends fuml.syntax.commonstructure.Element {

	public boolean isSubstitutable = true;
	public fuml.syntax.classification.Classifier specific = null;
	public fuml.syntax.classification.Classifier general = null;

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.isSubstitutable = isSubstitutable;
	} // setIsSubstitutable

	public void setGeneral(fuml.syntax.classification.Classifier general) {
		this.general = general;
	} // setGeneral

	public void _setSpecific(fuml.syntax.classification.Classifier specific) {
		this.specific = specific;
	} // _setSpecific

} // Generalization
