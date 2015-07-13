package com.gestion.note.bll;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Note;
import com.gestion.note.db.DBConnection;
import com.gestion.note.db.DataBaseException;


/**
 * 
 * Cette classe permet de gérer les notes

 */

public class GestionNotes {

	private final static Logger LOG = Logger.getLogger(GestionNotes.class);

	/** Objet de GestionNotes du Singleton */
	private static GestionNotes gestionNotes = null;

	/**
	 * interdire l'instanciation
	 */
	private GestionNotes() {

	}

	/**
	 * méthode qui crée une instance uniquement s'il n'en existe pas encore.
	 * Sinon elle renvoie une référence vers l'objet qui existe déjà
	 * 
	 * @return l'unique instance de cette classe
	 * 
	 */
	public static GestionNotes getInstance() {
		if (gestionNotes == null) {
			gestionNotes = new GestionNotes();
		}
		return gestionNotes;
	}

	/**
	 * Récuperer la note d'un etudiant dans une matière
	 * 
	 * @param idMat
	 * @param idEtud
	 * @param pAnnee
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	
	public Note getNotes(Long idMat, Long idEtud, int pAnnee) throws DataBaseException, ElementNotFoundException {
		Connection connect = DBConnection.getInstance();
		List<Note> lList = new ArrayList<Note>();
		try {
			PreparedStatement findStatement = connect
					.prepareStatement("SELECT * FROM NOTE where Etudiant_ID=? and Matiere_ID=? and annee=?");
			findStatement.setLong(1, idEtud);
			findStatement.setLong(2, idMat);
			findStatement.setInt(3, pAnnee);
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				double noteSN = result.getDouble("NOTESN");
				double noteSR = result.getDouble("NOTESR");
				int annee = result.getInt("ANNEE");
				int semestre = result.getInt("SEMESTRE");
				int id = result.getInt("Matiere_ID");

				Note lNote = new Note(Long.valueOf(id), noteSN, noteSR, semestre, annee);
				lList.add(lNote);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("The Object Note si not found in data base ");

		return lList.get(0);

	}

	
	/**Méthode qui calcule la moyenne d'un étudiant pour un module*/
	
	public double calculateMoyenne(Long idEtudiant,Long idModule,int pAnnee) throws DataBaseException, ElementNotFoundException
	{
		
	GestionModules lGestModules = null;
	GestionNotes lGestNotes = null;
	double moyenne = 0;
	lGestModules = GestionModules.getInstance();
	lGestNotes = GestionNotes.getInstance();
	List<Matiere> listMatieres = lGestModules.getMatieresModule(idModule);
       int idMat=0;
       Long idEtud = idEtudiant;
       int pnnee =pAnnee;
       
       int coeff=0;
       double noteSelectioner;
	for(int i =0;i<listMatieres.size();i++)
	{
		
		Note note=lGestNotes.getNotes(listMatieres.get(i).getId(), idEtud, pnnee);
		
		if(note.getNoteSN()>=0 || note.getNoteSR()>=0){
			if(note.getNoteSR()<0)
				noteSelectioner=note.getNoteSN();
			else
				noteSelectioner=note.getNoteSR();
				moyenne += listMatieres.get(i).getCoeff() *noteSelectioner ;
				
				coeff+=listMatieres.get(i).getCoeff();
		}
		
		
	}
	
		return moyenne/coeff;
	}
	
	
	
	/**
	 * Récupère la note d'une étudiant d'une matière donnée et une année donnée
	 * 
	 * @param idMat
	 *            id de la matière
	 * @param idEtud
	 *            id de l'étudiant
	 * @param pAnnee
	 *            l'année
	 * @return la note de l'étudiants
	 * @throws DataBaseException
	 *             en cas de problème de type base de données
	 * @throws ElementNotFoundException
	 */
	public Note getNote(int idMat, int idEtud, int pAnnee) throws DataBaseException, ElementNotFoundException {
		Connection connect = DBConnection.getInstance();
		List<Note> lList = new ArrayList<Note>();
		try {
			PreparedStatement findStatement = connect
					.prepareStatement("SELECT * FROM NOTE where Etudiant_ID=? and Matiere_ID=? and annee=?");
			findStatement.setInt(1, idEtud);
			findStatement.setInt(2, idMat);
			findStatement.setInt(3, pAnnee);
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				double noteSN = result.getDouble("NOTESN");
				double noteSR = result.getDouble("NOTESR");
				int annee = result.getInt("ANNEE");
				int semestre = result.getInt("SEMESTRE");
				int id = result.getInt("Matiere_ID");

				Note lNote = new Note(Long.valueOf(id), noteSN, noteSR, semestre, annee);
				lList.add(lNote);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("The Object Note si not found in data base ");

		return lList.get(0);

	}

	/**
	 * Permet de mettre à jour la note SN
	 * 
	 * @param idNote
	 * @param note
	 * @throws DataBaseException
	 */
	public void updateNoteSN(int idMat, int idEtud, int pAnnee, double note) throws DataBaseException {
		Connection connect = DBConnection.getInstance();
		String lUpdateNote = "UPDATE  NOTE" + " SET NOTESN =? " + "where Etudiant_ID=? and Matiere_ID=? and annee=?";

		PreparedStatement lUpdateNoteStatement;
		try {
			lUpdateNoteStatement = connect.prepareStatement(lUpdateNote);
			lUpdateNoteStatement.setDouble(1, note);
			lUpdateNoteStatement.setInt(2, idEtud);
			lUpdateNoteStatement.setInt(3, idMat);
			lUpdateNoteStatement.setInt(4, pAnnee);
			lUpdateNoteStatement.executeUpdate();
			LOG.debug("SQL Query Executed : UPDATE  NOTE" + " SET NOTESN =" + note + " where Etudiant_ID=" + idEtud
					+ " and Matiere_ID=" + idMat + " and annee=" + pAnnee);
		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

	}

	/**
	 * Permet d'affecter une note à un étudiant
	 * 
	 * @param idNote
	 * @param note
	 * @throws DataBaseException
	 */
	public void SaveNote(Note pNote, int idMat, int idEtudiant) throws DataBaseException {
		Connection connect = DBConnection.getInstance();

		String lSaveNote = "INSERT INTO NOTE " + "   (NOTESN,NOTESR,ANNEE,SEMESTRE,MATIERE_ID,ETUDIANT_ID,NOTE_ID) "
				+ "VALUES (?, ?, ?, ?, ?, ?,default)";

		PreparedStatement lUpdateNoteStatement;
		try {
			lUpdateNoteStatement = connect.prepareStatement(lSaveNote);
			lUpdateNoteStatement.setDouble(1, pNote.getNoteSN());
			lUpdateNoteStatement.setDouble(2, pNote.getNoteSR());

			lUpdateNoteStatement.setInt(3, pNote.getAnnee());
			lUpdateNoteStatement.setInt(4, pNote.getSemestre());
			lUpdateNoteStatement.setInt(5, idMat);
			lUpdateNoteStatement.setInt(6, idEtudiant);

			lUpdateNoteStatement.executeUpdate();
		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

	}

	/**
	 * Permet de mettre à jour la note SR
	 * 
	 * @param idNote
	 * @param note
	 * @throws DataBaseException
	 */
	public void updateNoteSR(int idMat, int idEtud, int pAnnee, double note) throws DataBaseException {
		Connection connect = DBConnection.getInstance();
		String lUpdateNote = "UPDATE  NOTE" + " SET NOTESR =? " + "where Etudiant_ID=? and Matiere_ID=? and annee=?";
		PreparedStatement lUpdateNoteStatement;
		try {
			lUpdateNoteStatement = connect.prepareStatement(lUpdateNote);
			lUpdateNoteStatement.setDouble(1, note);
			lUpdateNoteStatement.setInt(2, idEtud);
			lUpdateNoteStatement.setInt(3, idMat);
			lUpdateNoteStatement.setInt(4, pAnnee);
			lUpdateNoteStatement.executeUpdate();
			LOG.debug("SQL Query Executed : UPDATE  NOTE" + " SET NOTESR =" + note + " where Etudiant_ID=" + idEtud
					+ " and Matiere_ID=" + idMat + " and annee=" + pAnnee);

		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

	}
		
}
	
	
