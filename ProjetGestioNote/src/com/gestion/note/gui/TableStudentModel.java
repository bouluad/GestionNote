package com.gestion.note.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionNotes;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.EtudiantNote;
import com.gestion.note.bo.Note;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;


/**
 * Modele du Table
 
 */
public class TableStudentModel extends AbstractTableModel {

	/** Les colonnes de la table */
	private String[] colonnes = { "Nom", "Prénom", "CNE","Note SN", "Note SR" };

	/** les données du tableau */
	private List<EtudiantNote> lignes = new ArrayList<EtudiantNote>();

	private int stat;
	
	public TableStudentModel() {

	}

	/**
	 * Cette méthode permet de mettre à jour le modèle
	 * 
	 * @param listStudents
	 * @throws DataBaseException
	 */
	public void updae(List<Etudiant> listStudents, String pIntitule,int s) throws DataBaseException {

		// Effacer la liste
		lignes.clear();
		int id =0;
		
		stat=s;
		
		try {
			id = GestionModules.getInstance().getMatiereByName(pIntitule).getId().intValue();
		} catch (ElementNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		GestionNotes gsNotes = GestionNotes.getInstance();
		int i;
		for (i = 0; i < listStudents.size(); i++) {
			Note note = null;
			EtudiantNote lEtNote = null;
			try {
				note = gsNotes.getNote(id, listStudents.get(i).getId().intValue(), Integer
						.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)));

			} catch (ElementNotFoundException e) {
				// Si l'étudiant n'as une note dans ce module on lui affecte la
				// note par défaut (-1,-1)

				note = new Note();

			}

			lEtNote = new EtudiantNote(listStudents.get(i), note);
			lignes.add(lEtNote);

			// Signaler la modification des données du modèle
			fireTableChanged(new TableModelEvent(this, i, i, 0));
			fireTableChanged(new TableModelEvent(this, i, i, 1));
			fireTableChanged(new TableModelEvent(this, i, i, 2));
			fireTableChanged(new TableModelEvent(this, i, i, 3));
			fireTableChanged(new TableModelEvent(this, i, i, 4));
			
		}

	}

	/**
	 * rendre éditable les colonnes note SN et note SR
	 */
	public boolean isCellEditable(int ligne, int colonne) {

		
		
		if (colonne == 3 ||( colonne == 4 && stat==1))
			return true;

		return false;

	}

	public int getRowCount() {
		return lignes.size();
	}

	public int getColumnCount() {
		return colonnes.length;
	}

	public Object getValueAt(int ligne, int colonne) {

		EtudiantNote e = lignes.get(ligne);
		switch (colonne) {
		case 0:
			return e.getNom();
		case 1:
			return e.getPrenom();
		case 2:
			return e.getCne();
		case 3:
			if (e.getNote().getNoteSN() >= 0)
				return e.getNote().getNoteSN();
			else
				return "";

		case 4:
			if (e.getNote().getNoteSR() >= 0)
				return e.getNote().getNoteSR();
			else
				return "";

		default:
			return null;
		}

	}

	public void setValueAt(Object valeur, int ligne, int colonne) {

		EtudiantNote e = lignes.get(ligne);

		switch (colonne) {
		case 0:
			e.setNom((String) valeur);
			break;
		case 1:
			e.setPrenom((String) valeur);
			break;
		case 2:
			e.setCne((String) valeur);
			break;
		case 3:

			try {
				double noteSN = Double.parseDouble((String) valeur);
				e.getNote().setNoteSN(Double.parseDouble((String) valeur));
			} catch (NumberFormatException ex) {
				// Si la valeur saisie n'est pas de type numérique alors ne
				// modifie pas la valeur de la cellule est indiquer une erreur
				JOptionPane.showMessageDialog(null, "Valeur incorrecte, Entrer une valeur entre 0 et 20",
						"Valeur incorrecte", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 4:
			try {
				e.getNote().setNoteSR(Double.parseDouble((String) valeur));
			} catch (NumberFormatException ex) {
				// Si la valeur saisie n'est pas de type numérique alors ne
				// modifie pas la valeur de la cellule est indiquer une erreur
				JOptionPane.showMessageDialog(null, "Valeur incorrecte, Entrer une valeur entre 0 et 20",
						"Valeur incorrecte", JOptionPane.ERROR_MESSAGE);
			}
			break;

		default:
		}

		fireTableCellUpdated(ligne, colonne);

	}

	public String getColumnName(int colonne) {
		return colonnes[colonne];
	}

	public List<EtudiantNote> getLignes() {
		return lignes;
	}

	public void setLignes(List<EtudiantNote> lignes) {
		this.lignes = lignes;
	}

}