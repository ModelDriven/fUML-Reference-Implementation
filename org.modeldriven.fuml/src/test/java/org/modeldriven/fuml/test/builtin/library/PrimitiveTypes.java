/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.library;

import fUML.Syntax.Classes.Kernel.PrimitiveType;

import org.modeldriven.fuml.library.Library;

public class PrimitiveTypes extends org.modeldriven.fuml.FumlObject {

	public static final PrimitiveType Boolean = (PrimitiveType)Library.getInstance().lookup("Boolean");
	public static final PrimitiveType String = (PrimitiveType)Library.getInstance().lookup("String");
	public static final PrimitiveType Integer = (PrimitiveType)Library.getInstance().lookup("Integer");
	public static final PrimitiveType UnlimitedNatural = (PrimitiveType)Library.getInstance().lookup("UnlimitedNatural");
	public static final PrimitiveType Real = (PrimitiveType)Library.getInstance().lookup("Real");

} // PrimitiveTypes
