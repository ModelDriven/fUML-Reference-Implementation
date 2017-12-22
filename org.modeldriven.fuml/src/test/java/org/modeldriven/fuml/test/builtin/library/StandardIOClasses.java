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

import org.modeldriven.fuml.library.Library;

import fuml.syntax.structuredclassifiers.Class_;

public class StandardIOClasses {

	public static final Class_ Channel = (Class_)Library.getInstance().lookup("BasicInputOutput-Channel");
	public static final Class_ OutputChannel = (Class_)Library.getInstance().lookup("BasicInputOutput-OutputChannel");
	public static final Class_ TextOutputChannel = (Class_)Library.getInstance().lookup("BasicInputOutput-TextOutputChannel");
	public static final Class_ StandardOutputChannel = (Class_)Library.getInstance().lookup("BasicInputOutput-StandardOutputChannel");
	public static final Class_ InputChannel = (Class_)Library.getInstance().lookup("BasicInputOutput-InputChannel");

} // StandardIOClasses
