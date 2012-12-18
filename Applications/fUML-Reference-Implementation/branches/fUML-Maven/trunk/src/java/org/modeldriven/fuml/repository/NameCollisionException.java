package org.modeldriven.fuml.repository;


public class NameCollisionException extends RepositorylException {
	
    private static final long serialVersionUID = 1L;
    
    public NameCollisionException(Throwable t) {
		super(t);
	}
    public NameCollisionException(String msg) {
        super(msg);
    }

}
