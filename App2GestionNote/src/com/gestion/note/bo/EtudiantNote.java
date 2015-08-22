package com.gestion.note.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe utilisée pour stocker une ligne de la 
 * table liste des étudiants dans l IHM
 */

public class EtudiantNote extends Etudiant {

	private Note note;
	private Module module;
	private double moyenne;
	private List<Double> notes=new ArrayList<Double>();
	private String validation;
	
	private List<String> modules=new ArrayList<String>();

	public EtudiantNote(Etudiant e, double moyenne) {
		super(e);
		this.moyenne = moyenne;
	}

	public EtudiantNote(Etudiant e, Note note, double moyenne) {
		super(e);
		this.note = note;
		this.moyenne = moyenne;
	}
	
	public EtudiantNote(Etudiant e, List<Double> notes, double moyenne,String v) {
		super(e);
		this.notes = notes;
		this.moyenne = moyenne;
		this.validation=v;
	}

	public double getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}

	public EtudiantNote(Etudiant e, Note note, Module module) {
		super(e);
		this.note = note;
		this.module = module;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public EtudiantNote(Etudiant pEtudiant, Note pNote) {

		super(pEtudiant);
		note = pNote;

	}

	public EtudiantNote(Long id, String nom, String prenom, String cne, String cin, Date dateNais, String lieuNais,
			Note pNote) {
		super(id, nom, prenom, cne, cin, dateNais, lieuNais);
		note = pNote;
	}

	public EtudiantNote(Etudiant e, List<String> modulesNotes) {
		super(e);
		this.modules=modulesNotes;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}
	
	public Double getNote(int i) {
		return notes.get(i);
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	
}
