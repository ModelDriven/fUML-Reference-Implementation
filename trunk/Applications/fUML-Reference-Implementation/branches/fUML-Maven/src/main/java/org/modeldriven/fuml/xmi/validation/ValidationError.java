/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.xmi.validation;

import java.text.MessageFormat;
import java.util.Locale;

import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.XmiReference;

/**
 * Contains detailed error information sufficient for generation of formatted, informative
 * error messages for use in simple logging as well as web displays. Errors are categorized and have
 * a associated severity as well as source information including element and location
 * information.
 *
 * @author Scott Cinnamond
 */
public class ValidationError {

	private final static String DELIM = ".";
	private final static String LOCATION = "LOCATION";

	private ErrorSeverity severity;
	private ErrorCode code;
	private XmiNode node;
	private XmiReference reference;
	private String id;
	private String propertyName = "unknown";

	public ValidationError(XmiNode node, ErrorCode code, ErrorSeverity severity) {
		super();
		this.node = node;
		this.code = code;
		this.severity = severity;
	}

	public ValidationError(XmiNode node, String propertyName,
			ErrorCode code, ErrorSeverity severity) {
		super();
		this.node = node;
		this.propertyName = propertyName;
		this.code = code;
		this.severity = severity;
	}

	public ValidationError(XmiReference reference, String id,
			ErrorCode code, ErrorSeverity severity) {
		super();
		this.reference = reference;
		this.id = id;
		this.code = code;
		this.severity = severity;
	}

	public ErrorSeverity getSeverity() {
		return severity;
	}

	public ErrorCode getCode() {
		return code;
	}

	public String getCodeText()
	{
		String value = code.value();
		String text = ValidationProperties.instance().getProperty(value,
				value);
		return text;
	}

	public String getLocationText()
	{
		String text = ValidationProperties.instance().getProperty(
				this.getClass().getSimpleName() + DELIM + LOCATION);

		// FIXME: use a factory pattern for this crap
		if (this.node != null)
		{
			Object[] params = {
					this.node.getLineNumber(),
					this.node.getColumnNumber()
				};
			return substituteParams(text, params);
		}
		else
		{
			Object[] params = {
					this.reference.getLineNumber(),
					this.reference.getColumnNumber()
				};
			return substituteParams(text, params);
		}
	}

	public ErrorCategory getCategory() {
		return code.getCategory();
	}

	public String getCategoryText()
	{
		String value = getCategory().value();
		String text = ValidationProperties.instance().getProperty(value,
				value);
		return text;
	}

	public String getText()
	{
		String value = code.value();
		String text = ValidationProperties.instance().getProperty(
				ErrorCode.class.getSimpleName() + DELIM + value,
				value);
		if (code.ordinal() == ErrorCode.UNDEFINED_CLASS.ordinal() ||
			code.ordinal() == ErrorCode.ABSTRACT_CLASS_INSTANTIATION.ordinal())
		{
			Object[] params = {
			    getLocationText(),
				this.node.getLocalName()
			};
			text = substituteParams(text, params);
		}
		else if (code.ordinal() == ErrorCode.UNDEFINED_PROPERTY.ordinal() ||
				 code.ordinal() == ErrorCode.PROPERTY_REQUIRED.ordinal() ||
				 code.ordinal() == ErrorCode.DERIVED_PROPERTY_INSTANTIATION.ordinal())
		{
			Object[] params = {
				    getLocationText(),
					this.node.getLocalName(),
					this.propertyName
				};
				text = substituteParams(text, params);
		}
        else if (code.ordinal() == ErrorCode.DUPLICATE_REFERENCE.ordinal())
        {
            Object[] params = {
                    getLocationText(),
                    this.node.getLocalName(),
                    this.node.getXmiId()
                };
                text = substituteParams(text, params);
        }
		else if (code.ordinal() == ErrorCode.INVALID_REFERENCE.ordinal() ||
		         code.ordinal() == ErrorCode.INVALID_EXTERNAL_REFERENCE.ordinal())
		{
			if (this.reference != null)
			{
			Object[] params = {
				    getLocationText(),
					this.reference.getLocalName(),
					this.id
				};
				text = substituteParams(text, params);
			}
		}
		else
		{
			Object[] params = {
				getLocationText(),
				this.node.getLocalName()
			};
			text = substituteParams(text, params);
		}

		return text;
	}

    private String substituteParams(String msgtext, Object params[]) {
    	return substituteParams(null, msgtext, params);
    }

    /**
     * Uses <code>MessageFormat</code> and the supplied parameters to fill in
     * the param placeholders in the String.
     *
     * @param locale
     *                The <code>Locale</code> to use when performing the
     *                substitution.
     * @param msgtext
     *                The original parameterized String.
     * @param params
     *                The params to fill in the String with.
     * @return The updated String.
     */
    private String substituteParams(Locale locale, String msgtext, Object params[])
    {
		String localizedStr = null;
		if (params == null || msgtext == null)
		    return msgtext;
		MessageFormat mf = new MessageFormat(msgtext);
		if (locale != null)
		{
		    mf.setLocale(locale);
		    localizedStr = mf.format(((Object) (params)));
		}
		else
		{
		    localizedStr = mf.format(((Object) (params)));
		}
		return localizedStr;
    }

    public String toString()
    {
    	return this.getText();
    }
}
