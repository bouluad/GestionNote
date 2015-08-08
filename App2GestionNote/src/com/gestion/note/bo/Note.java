package com.gestion.note.bo;

/**
 * Classe qui présente une Note (note SN, SR ...)
 * 
 */
public class Note {

	private Long id;
	private double noteSN;
	private double noteSR;
	private int semestre;
	private int annee;

	public Note() {
		this.id = Long.valueOf(-1);
		this.noteSN = -1;
		this.noteSR = -1;
		this.semestre = -1;
		this.annee = -1;

	}

	public Note(Long pId, double noteSN, double noteSR, int semestre, int annee) {
		this.id = pId;
		this.noteSN = noteSN;
		this.noteSR = noteSR;
		this.semestre = semestre;
		this.annee = annee;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getNoteSN() {
		return noteSN;
	}

	public void setNoteSN(double noteSN) {
		this.noteSN = noteSN;
	}

	public double getNoteSR() {
		return noteSR;
	}

	public void setNoteSR(double noteSR) {
		this.noteSR = noteSR;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

}
