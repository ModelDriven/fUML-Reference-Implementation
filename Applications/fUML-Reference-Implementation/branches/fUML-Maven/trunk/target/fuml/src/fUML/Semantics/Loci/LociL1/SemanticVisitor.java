
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

package fUML.Semantics.Loci.LociL1;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public abstract class SemanticVisitor extends org.modeldriven.fuml.FumlObject {

	public static void _endIsolation() {
		Debug.println("[_endIsolation] End isolation.");
	} // _endIsolation

	public static void _beginIsolation() {
		Debug.println("[_beginIsolation] Begin isolation.");
	} // _beginIsolation

} // SemanticVisitor
