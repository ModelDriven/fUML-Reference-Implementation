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

/**
 * Represents various severity levels for error conditions encountered during
 * validation processing. Such severities are intended to be processing
 * context-specific. For instance, errors encountered during a simple compliance
 * check, may be linked to warning-level severities, while errors encountered
 * during assembly of a target java object-model may be linked to a fatal
 * severity.
 * 
 * @author Scott Cinnamond
 */
public enum ErrorSeverity {

    FATAL("FATAL"), WARN("WARN"), INFO("INFO");

    private final String value;

    ErrorSeverity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorSeverity fromValue(String v) {
        for (ErrorSeverity c : ErrorSeverity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
