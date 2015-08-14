package com.gestion.note.bll;

/**
 * Classe d'exception

 */
public class ElementNotFoundException extends Exception {

	public ElementNotFoundException() {
		super();
	}

	public ElementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementNotFoundException(String message) {
		super(message);
	}

	public ElementNotFoundException(Throwable cause) {
		super(cause);
	}

}
