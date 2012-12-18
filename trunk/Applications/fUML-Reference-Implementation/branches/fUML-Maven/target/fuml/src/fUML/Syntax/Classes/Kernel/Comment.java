
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

package fUML.Syntax.Classes.Kernel;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class Comment extends org.modeldriven.fuml.FumlObject {

	public fUML.Syntax.Classes.Kernel.ElementList annotatedElement = new fUML.Syntax.Classes.Kernel.ElementList();
	public String body = "";

	public void setAnnotatedElement(
			fUML.Syntax.Classes.Kernel.Element annotatedElement) {
		this.annotatedElement.add(annotatedElement);
	} // setAnnotatedElement

	public void setBody(String body) {
		this.body = body;
	} // setBody

} // Comment
