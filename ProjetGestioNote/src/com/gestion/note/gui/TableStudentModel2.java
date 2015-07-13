package com.gestion.note.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionNotes;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.EtudiantNote;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Note;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;




// Model de la table qui affiche la moyenne d'un modele choisi pour la liste des étudiants d'une classe



public class TableStudentModel2 extends AbstractTableModel
{   
	
	/** Les colonnes de la table */
	//private String[] colonnes = { "Nom", "Prénom", "CNE","Moyenne"};
	private List<String> colonnes=new ArrayList<String>();
	/** les données du tableau */
	private List<EtudiantNote> lignes = new ArrayList<EtudiantNote>();
	
	
	private int matiereSize=0;
	
	public TableStudentModel2() {

	}
	
	public TableStudentModel2(List<String> st) {
		
		colonnes.add("CNE");
		colonnes.add("Nom");
		colonnes.add("Prenom");
		for (int i = 0; i < st.size(); i++) {
			colonnes.add(st.get(i));
		}
		colonnes.add("Moyenne");
		colonnes.add("Validation");
	}

	
	public void update(List<Etudiant> listStudents, String pIntitule,String className) throws DataBaseException {

		// Effacer la liste
		lignes.clear();
		Long id = Long.valueOf(0);
		id = GestionModules.getInstance().getModuleId(pIntitule);

		GestionNotes gsNotes = GestionNotes.getInstance();
		int i;
		for (i = 0; i < listStudents.size(); i++) {
			Double moyenn = null;
			
			EtudiantNote lEtNote = null;
			try {
				moyenn = gsNotes.calculateMoyenne((long)listStudents.get(i).getId().intValue(),(long)id, Integer
						.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)));

			} catch (ElementNotFoundException e) {
				
			}
			
		GestionModules lGestModules  = GestionModules.getInstance();	
		List<Matiere> listMatieres;
		
		List<Double> listNotes=new ArrayList<Double>();
		try {
			listMatieres = lGestModules.getMatieresModule(id);
			matiereSize=listMatieres.size();
			for(int j=0;j<listMatieres.size();j++){
				listNotes.add(getNoteByIdEtAndMat((long)listStudents.get(i).getId().intValue(),(long)listMatieres.get(j).getId().intValue(), Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV))));
				System.out.println("note:"+getNoteByIdEtAndMat((long)listStudents.get(i).getId().intValue(),(long)listMatieres.get(j).getId().intValue(), Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV))));
			}
			
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
		}
			
			lGestModules.updateMoyenneModule((long)listStudents.get(i).getId().intValue(), id, moyenn, testValidation(moyenn, className));
			
			lEtNote = new EtudiantNote(listStudents.get(i), listNotes,moyenn,testValidation(moyenn, className));
			lignes.add(lEtNote);

			// Signaler la modification des données du modèle
			
			fireTableChanged(new TableModelEvent(this, i, i, 0));
			fireTableChanged(new TableModelEvent(this, i, i, 1));
			fireTableChanged(new TableModelEvent(this, i, i, 2));
			fireTableChanged(new TableModelEvent(this, i, i, 3)); 
		}

	}
	
	private String testValidation(double m,String className){
		
		if(className.startsWith("C") && (m>=Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.SEUILCP))))
			return "V";
		if(className.startsWith("C") && (m<Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.SEUILCP))))
			return "NV";
		if(className.startsWith("G") && (m>=Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.SEUILCI))))
			return "V";
		if(className.startsWith("G") && (m<Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.SEUILCI))))
			return "NV";
		else return "undefined";
	}
	
	private Double getNoteByIdEtAndMat(Long idEt,Long idMat,int annee) throws DataBaseException, ElementNotFoundException{
		
		GestionNotes lGestNotes = GestionNotes.getInstance();
		Note note=lGestNotes.getNotes(idMat, idEt, annee);
		if(note.getNoteSN()<0 && note.getNoteSR()<0){
			return -99.0;
			
		}
		else{
			if(note.getNoteSR()<0)
				return note.getNoteSN();
			else
				return note.getNoteSR();
		}	
	}
	
	
	public int getRowCount() {
		return lignes.size();
	}

	public int getColumnCount() {
		return colonnes.size();
	}

	public Object getValueAt(int ligne, int colonne) {
		EtudiantNote e = lignes.get(ligne);
		
		if(colonne == 0)  	return e.getCne();
		if(colonne == 1) 	return e.getNom();
		if(colonne == 2) 	return e.getPrenom();
		
		if(colonne >=3 && colonne < matiereSize+3){
			return e.getNote(colonne-3);
		}		
		if(colonne== matiereSize+3) {
			boolean test=true;
			for(int i=0;i<matiereSize;i++){
				
				if(e.getNote(i)==-99) test=false;
			}
			if(test)
				return e.getMoyenne();
			else return -99.0;
		}
		if(colonne == matiereSize+4) {
			boolean test=true;
			for(int i=0;i<matiereSize;i++){
				
				if(e.getNote(i)==-99) test=false;
			}
			if(test)
				return e.getValidation();
			else return "undefined";
		}
		else return null;
		
	}
	
	
	public String getColumnName(int colonne) {
		return colonnes.get(colonne);
	}

	public List<EtudiantNote> getLignes() {
		return lignes;
	}

	public void setLignes(List<EtudiantNote> lignes) {
		this.lignes = lignes;
	}


}
