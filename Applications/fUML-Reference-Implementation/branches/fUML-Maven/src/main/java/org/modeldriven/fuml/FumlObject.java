package org.modeldriven.fuml;

import javax.xml.namespace.QName;


public class FumlObject {
    private String xmiId;
    private String href;
    private String xmiNamespace;

    public String getXmiId() {
        return xmiId;
    }

    public void setXmiId(String xmiId) {
        this.xmiId = xmiId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

	public String getXmiNamespace() {
		return xmiNamespace;
	}

	public void setXmiNamespace(String namespace) {
		this.xmiNamespace = namespace;
	}

}
