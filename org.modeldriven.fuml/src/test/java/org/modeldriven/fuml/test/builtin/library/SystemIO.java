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

import fuml.syntax.activities.Activity;
import fuml.syntax.commonbehavior.Behavior;

public class SystemIO {

	public static final Behavior WriteLine = 
			(Activity)Library.getInstance().lookup("BasicInputOutput-WriteLine");

} // SystemIO
