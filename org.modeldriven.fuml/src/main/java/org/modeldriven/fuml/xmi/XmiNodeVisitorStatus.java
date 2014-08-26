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

/**
 * A class used to communicate traversal logic changes required by a client.
 * 
 * @author Scott Cinnamond
 */
public class XmiNodeVisitorStatus {

    public static final int STATUS_TRAVERSE = 1;
    public static final int STATUS_ABORT = 2;
    private int status = STATUS_TRAVERSE;

    public XmiNodeVisitorStatus() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
