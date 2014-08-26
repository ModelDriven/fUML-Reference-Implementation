package org.modeldriven.fuml.io;

import java.io.InputStream;

import org.modeldriven.fuml.repository.RepositoryArtifact;

public class ResourceArtifact implements RepositoryArtifact {

	private InputStream stream;
	private String name;
	private String urn;
	private String namespaceURI;
	
	@SuppressWarnings("unused")
	private ResourceArtifact() {};
	
	public ResourceArtifact(String urn,
			String namespaceUri,
			InputStream stream) {
		this.name = urn;
		this.urn = urn;
		this.namespaceURI = namespaceUri;
		this.stream = stream;
	}

	public String getName() {
		return this.name;
	}
	
	public String getURN() {
		return this.urn;
	}

	public InputStream getInputStream() {
		return stream;
	}

	public String getNamespaceURI() {
		return this.namespaceURI;
	}

}
