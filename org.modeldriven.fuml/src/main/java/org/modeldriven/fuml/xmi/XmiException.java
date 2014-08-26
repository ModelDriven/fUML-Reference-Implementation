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

import org.modeldriven.fuml.FumlException;

/**
 * 
 * @author Scott Cinnamond
 */
public class XmiException extends FumlException {
	
	public XmiException(Throwable t) {
		super(t);
	}
    public XmiException(String msg) {
        super(msg);
    }

}
