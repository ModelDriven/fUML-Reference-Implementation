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

package org.modeldriven.fuml.xmi.validation;

import org.modeldriven.fuml.xmi.XmiException;

/**
 * 
 * @author Scott Cinnamond
 */
public class ValidationException extends XmiException {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private ValidationException() {
        super("not implemented");
    }
    
    public ValidationException(String msg) {
        super(msg);
    }

	public ValidationException(ValidationError error) {
		super(error.getText());
	}
}
