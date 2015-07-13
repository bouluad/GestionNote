package com.gestion.note.gui;

import java.util.ArrayList;
import java.util.List;

import com.gestion.note.bo.Etudiant;

public class DialogChoixModuleModel {
	
	private String slectedClasse;
	private String slectedModule;
	

	private List<Etudiant> listStudents = new ArrayList<Etudiant>();


	public String getSlectedClasse() {
		return slectedClasse;
	}


	public void setSlectedClasse(String slectedClasse) {
		this.slectedClasse = slectedClasse;
	}


	public String getSlectedModule() {
		return slectedModule;
	}


	public void setSlectedModule(String slectedModule) {
		this.slectedModule = slectedModule;
	}


	public List<Etudiant> getListStudents() {
		return listStudents;
	}


	public void setListStudents(List<Etudiant> listStudents) {
		this.listStudents = listStudents;
	}



}
