package org.modeldriven.fuml.io;

import java.io.File;

import org.modeldriven.fuml.repository.RepositoryArtifact;

public class FileArtifact implements RepositoryArtifact {

	private File file;
	private String namespaceURI;
	
	@SuppressWarnings("unused")
	private FileArtifact() {};

	private FileArtifact(File file) {
		super();
		this.file = file;
	}
	
	public FileArtifact(File file, String namespaceURI) {
		this(file);
		this.namespaceURI = namespaceURI;
	}

	public String getURN() {
		return this.file.getName();
	}

	public File getFile() {
		return file;
	}

	public String getNamespaceURI() {
		return this.namespaceURI;
	}

}
