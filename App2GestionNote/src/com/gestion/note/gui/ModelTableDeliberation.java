package com.gestion.note.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionClasses;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionNotes;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.EtudiantNote;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.bo.Note;
import com.gestion.note.config.Configuration;
import com.gestion.note.db.DataBaseException;
import com.gestion.note.outils.headerLine;


// Model de la table deleberation


public class ModelTableDeliberation extends AbstractTableModel
{   
	
	/** Les colonnes de la table */
	private List<String> colonnes=new ArrayList<String>();
	/** les données du tableau */
	private List<EtudiantNote> lignes = new ArrayList<EtudiantNote>();
	
	private int tableSize=0;
	private int matiereSize=0;
	
	public ModelTableDeliberation() {

	}
	
	public ModelTableDeliberation(Long idClasse) {
		
		GestionModules lGestModules  = GestionModules.getInstance();	
		Long id = Long.valueOf(0);
		
		try {
			List<Module> listModules=lGestModules.getClasseModules(idClasse);
			
			colonnes.add("<html>CNE"+"<br/>."+"</html>");
			colonnes.add("Nom");
			colonnes.add("Prenom");
			
			for(int i=0;i<listModules.size();i++)
			{
				List<Matiere> l=lGestModules.getMatieresModule(listModules.get(i).getId());
				
				for(int j=0;j<l.size();j++)
				{
					//l.get(j).getIntitule()
					
					
				headerLine hl=new headerLine(l.get(j).getIntitule());
					colonnes.add("<html>"+hl.getT()+"<br/>"+hl.getT2()+"</html>");
				}
				colonnes.add("Moyenne");
				colonnes.add("Validation");
			}
			
			colonnes.add("<html>Moyenne<br/>generale</html>");
			colonnes.add("<html>Validation<br/> de l'année</html>");
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void update(List<Etudiant> listStudents, Long idClasse) throws DataBaseException, ElementNotFoundException {
		
		
		// Effacer la liste
		lignes.clear();
		GestionModules lGestModules  = GestionModules.getInstance();	
		GestionNotes gsNotes = GestionNotes.getInstance();
		GestionClasses lGestClasse  = GestionClasses.getInstance();	


		List<Module> listModules=lGestModules.getClasseModules(idClasse);

		
		for (int i = 0; i < listStudents.size(); i++) {
			EtudiantNote lEtNote = null;
			List<String> modulesNotes=new ArrayList<String>();
			Double moyenneGenerale=0.0;
			
			System.out.println("etu :"+listStudents.get(i).getNom());
			for(int j=0;j<listModules.size();j++){
				System.out.println("mat :"+listModules.get(j).getIntitule());

				List<Matiere> listMatieres;
				
				try {
					listMatieres = lGestModules.getMatieresModule((long)listModules.get(j).getId());
					matiereSize=listMatieres.size();
					
					for(int k=0;k<listMatieres.size();k++){
						
						System.out.println("mat :"+listMatieres.get(k).getIntitule());
						Double noteM=getNoteByIdEtAndMat((long)listStudents.get(i).getId().intValue(),(long)listMatieres.get(k).getId().intValue()
								,Configuration.getInstance().getPropertie().getAnneeuniv());
						
						modulesNotes.add(noteM.toString());
					}
					
				} catch (ElementNotFoundException e) {
					e.printStackTrace();
				}
				
				
				Double moyenn = null;
				try {
					moyenn = gsNotes.calculateMoyenne((long)listStudents.get(i).getId().intValue(),(long)listModules.get(j).getId(),
							Configuration.getInstance().getPropertie().getAnneeuniv());
					moyenneGenerale+=moyenn;
					modulesNotes.add(moyenn.toString());
	
				} catch (ElementNotFoundException e) {
					//TODO
				}	
				
				modulesNotes.add(testValidation(moyenn, lGestClasse.getClasseById(idClasse).getNom()));	
			//TODO	
			//lGestModules.updateMoyenneModule((long)listStudents.get(i).getId().intValue(), id, moyenn, testValidation(moyenn, className));

			}
			moyenneGenerale=moyenneGenerale/listModules.size();
			modulesNotes.add(moyenneGenerale.toString());
			modulesNotes.add(testValidation(moyenneGenerale, lGestClasse.getClasseById(idClasse).getNom()));
			tableSize=modulesNotes.size();
			
			lEtNote = new EtudiantNote(listStudents.get(i), modulesNotes);
			lignes.add(lEtNote);

			// Signaler la modification des données du modèle
			
			fireTableChanged(new TableModelEvent(this, i, i, 0));
			fireTableChanged(new TableModelEvent(this, i, i, 1));
			fireTableChanged(new TableModelEvent(this, i, i, 2));
			fireTableChanged(new TableModelEvent(this, i, i, 3)); 
		}

	}
	
	private String testValidation(double m,String className) throws DataBaseException, ElementNotFoundException{
			
		if(className.startsWith("C") && (m>=Configuration.getInstance().getPropertie().getSeuilCp()))
			return "V";
		if(className.startsWith("C") && (m<Configuration.getInstance().getPropertie().getSeuilCp()))
			return "NV";
		if(className.startsWith("G") && (m>=Configuration.getInstance().getPropertie().getSeuilCi()))
			return "V";
		if(className.startsWith("G") && (m<Configuration.getInstance().getPropertie().getSeuilCi()))
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
		
		
		if(colonne >=3 && colonne < tableSize+3) {
			
			return e.getModules().get(colonne-3);
		}
		
//		if(colonne >=3 && colonne < matiereSize+3){
//			return e.getNote(colonne-3);
//		}		
//		if(colonne== matiereSize+3) {
//			boolean test=true;
//			for(int i=0;i<matiereSize;i++){
//				
//				if(e.getNote(i)==-99) test=false;
//			}
//			if(test)
//				return e.getMoyenne();
//			else return -99.0;
//		}
//		if(colonne == matiereSize+4) {
//			boolean test=true;
//			for(int i=0;i<matiereSize;i++){
//				
//				if(e.getNote(i)==-99) test=false;
//			}
//			if(test)
//				return e.getValidation();
//			else return "undefined";
//		}
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
