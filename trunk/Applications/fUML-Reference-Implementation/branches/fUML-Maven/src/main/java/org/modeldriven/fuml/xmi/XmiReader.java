/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.xmi;

import java.io.InputStream;
import java.util.Collection;


/**
 * Reader interface to abstract multiple XMI import processing approaches, for 
 * instance JAXB imports and StAX stream imports. 
 * 
 * @author Scott Cinnamond
 */
public interface XmiReader {
    
	
	@SuppressWarnings("unchecked")
    public Collection read(InputStream stream);

}
