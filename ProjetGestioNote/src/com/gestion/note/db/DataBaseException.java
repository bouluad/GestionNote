package com.gestion.note.db;

/**
 * Classe d'exception en cas d'erreur de type base de données
 * 
 * 01/10/2011
 * 
 * @author Tarik BOUDAA
 * 
 */
public class DataBaseException extends Exception {

	public DataBaseException(String pMsg, Throwable pCause) {
		super(pMsg, pCause);
	}

	public DataBaseException(Throwable pCause) {
		super(pCause);
	}

}
