
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

public class EnumerationLiteral extends
		fuml.syntax.classification.InstanceSpecification {

	public fuml.syntax.simpleclassifiers.Enumeration enumeration = null;
	public fuml.syntax.simpleclassifiers.Enumeration classifier = null;

	public void _setEnumeration(
			fuml.syntax.simpleclassifiers.Enumeration enumeration) {
		super.addClassifier(enumeration);
		this.classifier = enumeration;
		this.enumeration = enumeration;
	} // _setEnumeration

} // EnumerationLiteral
