package com.gestion.note.bo;


/**
 * Classe qui présente une Matière 
 */
public class Matiere {
	
	private Long id;
	private String intitule;
	private int nbrheure;
	private double coeff;
	private Long idProf;
	private Long idModule;
	
	
	public Matiere(Long id ,Long idModule,String intitule, int nbrheure, double coeff,Long idProf) {
	
		this.id = id;
		this.intitule = intitule;
		this.nbrheure = nbrheure;
		this.coeff = coeff;
		this.idProf=idProf;
		this.idModule=idModule;

	}
	public Matiere(Long id, String intitule, int nbrheure, double coeff) {
		
		this.id = id;
		this.intitule = intitule;
		this.nbrheure = nbrheure;
		this.coeff = coeff;

	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public int getNbrheure() {
		return nbrheure;
	}
	public void setNbrheure(int nbrheure) {
		this.nbrheure = nbrheure;
	}
	public double getCoeff() {
		return coeff;
	}
	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	public Long getIdProf() {
		return idProf;
	}

	public void setIdProf(Long idProf) {
		this.idProf = idProf;
	}
	public Long getIdModule() {
		return idModule;
	}
	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	
	
}
