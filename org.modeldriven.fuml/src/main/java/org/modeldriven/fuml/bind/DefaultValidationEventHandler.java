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
package org.modeldriven.fuml.bind;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultValidationEventHandler implements BindingValidationEventHandler {
	
    private static Log log = LogFactory.getLog(DefaultValidationEventHandler.class);
    private int errorCount;
    private boolean cumulative = true;
	
	public int getErrorCount() {
		return errorCount;
	}

	public DefaultValidationEventHandler() {}	
	public DefaultValidationEventHandler(boolean cumulative) {
		this.cumulative = cumulative;
	}	
	
    public boolean handleEvent(ValidationEvent ve) {
        boolean result = this.cumulative;
        this.errorCount++;        
        ValidationEventLocator vel = ve.getLocator();
        
        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
            + String.valueOf(vel.getOffset())
            + "] - " + ve.getMessage();
        
        switch (ve.getSeverity()) {
        case ValidationEvent.WARNING:
            log.warn(message);
            break;
        case ValidationEvent.ERROR:
            log.error(message);
            break;
        case ValidationEvent.FATAL_ERROR:
            log.fatal(message);
            break;
        default:
            log.error(message);
        }
        return result;
    }
    
    public void reset()
    {
    	this.errorCount = 0;
    }

}
