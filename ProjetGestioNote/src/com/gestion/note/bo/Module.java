package com.gestion.note.bo;

import java.util.List;

/**
 * Classe qui présente un Module
 * 
 */
public class Module {
	
	private Long id;
	private String code;
	private String intitule;
	private String version;
	private List<Matiere> matieres;
	
	public Module() {
		
	}
	
	public Module( String code, String intitule, String version,Long id) {
		
		
		this.code = code;
		this.intitule = intitule;
		this.version = version;
		this.id = id;
	}
	
	
	public Module(Long id, String code, String intitule, String version) {
		super();
		this.id = id;
		this.code = code;
		this.intitule = intitule;
		this.version = version;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<Matiere> getMatieres() {
		return matieres;
	}
	public void setMatieres(List<Matiere> matieres) {
		this.matieres = matieres;
	}
	

}
