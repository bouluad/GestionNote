package com.gestion.note.bo;

import java.util.Date;

/**
 *Classe qui présente un Etudiant
 * 
 */
public class Etudiant {

	private Long id;
	private String nom;
	private String prenom;
	private String cne;
	private String cin;
	private Date dateNais;
	private String lieuNais;

	public Etudiant(Etudiant e) {

		this.id = e.id;
		this.nom = e.nom;
		this.prenom = e.prenom;
		this.cne = e.cne;
		this.cin = e.cin;
		this.dateNais = e.dateNais;
		this.lieuNais = e.lieuNais;
	}

	public Etudiant(Long id, String nom, String prenom, String cne, String cin, Date dateNais, String lieuNais) {

		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.cne = cne;
		this.cin = cin;
		this.dateNais = dateNais;
		this.lieuNais = lieuNais;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Date getDateNais() {
		return dateNais;
	}

	public void setDateNais(Date dateNais) {
		this.dateNais = dateNais;
	}

	public String getLieuNais() {
		return lieuNais;
	}

	public void setLieuNais(String lieuNais) {
		this.lieuNais = lieuNais;
	}

}
