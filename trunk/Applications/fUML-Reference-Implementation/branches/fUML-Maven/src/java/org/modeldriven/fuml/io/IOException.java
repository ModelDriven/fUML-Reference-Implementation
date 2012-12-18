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
package org.modeldriven.fuml.io;

import org.modeldriven.fuml.FumlException;

public class IOException extends FumlException {

	private static final long serialVersionUID = 1L;

	public IOException(Throwable t) {
        super(t);
    }

    public IOException(String msg) {
        super(msg);
    }

}
