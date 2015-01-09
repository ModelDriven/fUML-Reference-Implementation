/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2015 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.library;

import org.modeldriven.fuml.FumlException;

public class LibraryException extends FumlException {
	
	private static final long serialVersionUID = 1L;
	
	public LibraryException(Throwable t) {
		super(t);
	}
    public LibraryException(String msg) {
        super(msg);
    }

}
