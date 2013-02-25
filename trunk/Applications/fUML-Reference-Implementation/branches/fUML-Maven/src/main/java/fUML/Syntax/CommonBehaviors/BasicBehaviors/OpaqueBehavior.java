
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

package fUML.Syntax.CommonBehaviors.BasicBehaviors;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class OpaqueBehavior extends
		fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior {

	public UMLPrimitiveTypes.StringList body = new UMLPrimitiveTypes.StringList();
	public UMLPrimitiveTypes.StringList language = new UMLPrimitiveTypes.StringList();

	public void addBody(String body) {
		this.body.add(body);
	} // addBody

	public void addLanguage(String language) {
		this.language.add(language);
	} // addLanguage

} // OpaqueBehavior
