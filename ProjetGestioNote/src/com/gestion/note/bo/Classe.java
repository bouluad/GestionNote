package com.gestion.note.bo;

import java.util.List;


/**
 * Classe Metier qui presente une classe d'étudiant
 */
public class Classe {

	private Long id;
	private String nom;
	private String code;
	private List<Etudiant> etudiants;

	public Classe(Long id, String nom, String code) {

		this.id = id;
		this.nom = nom;
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
