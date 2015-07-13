package com.gestion.note.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.gestion.note.bo.Classe;
import com.gestion.note.db.DBConnection;
import com.gestion.note.db.DataBaseException;


/**
 * Cette classe gère les professeurs
 * 
 *
 */

public class GestionProf {
	

	private final static Logger LOG = Logger.getLogger(GestionProf.class);

	
	/** Objet de GestionClasses du Singleton */
	private static final GestionProf gestionProf = new GestionProf();

	/**
	 * Interdire l'instanciation
	 */

	private  GestionProf() {
	}
	
	
	public static GestionProf getInstance() {

		return gestionProf;
	}
	
	
	/**
	 * Methode retourner la liste des professeurs
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	public List<String> getProf() throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<String> lList = new ArrayList<String>();

		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM professeur");
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String nom = result.getString("nom")+" "+result.getString("prenom");
	
				lList.add(nom);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("No record found in database ");

		return lList;

	}
	
	/**
	 * Methode pour configurer le propriétaire de l'application 
	 * 
	 * @param nom
	 * @param prenom
	 * @throws DataBaseException
	 * @throws SQLException
	 */
	
	
	public void updateStatProf(String nom,String prenom) throws DataBaseException, SQLException
	{
		int lid=-1;
		
		Connection connect = DBConnection.getInstance();
		String lUpdateNote = "UPDATE  Professeur" + " SET owner =? " + "where nom=? and prenom=?";
		
		String lUpdateNote2 = "UPDATE  Professeur" + " SET owner =? " + "where PROF_ID=?";
		
		PreparedStatement findStatement = connect.prepareStatement("select PROF_ID from Professeur where owner=1");
		ResultSet result = findStatement.executeQuery();
		while (result.next()) {
			 lid = result.getInt("PROF_ID");
			
		}
		
		PreparedStatement lUpdateNoteStatement;
		PreparedStatement lUpdateNoteStatement2;
		
		try {
			
			lUpdateNoteStatement2 = connect.prepareStatement(lUpdateNote2);
			lUpdateNoteStatement2.setInt(1,0);
			lUpdateNoteStatement2.setInt(2,lid);
			lUpdateNoteStatement2.executeUpdate();
						
			lUpdateNoteStatement = connect.prepareStatement(lUpdateNote);
			lUpdateNoteStatement.setInt(1,1);
			lUpdateNoteStatement.setString(2,nom);
			lUpdateNoteStatement.setString(3,prenom);
			lUpdateNoteStatement.executeUpdate();
			
			new JOptionPane()
			.showMessageDialog(null, "Le propriétaire maintenant est : "+nom+" "+prenom,"Propriétaire ", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

	}
	
	/**
	 * Le nom et prenom du propriétaire 
	 * 
	 * @return
	 * @throws DataBaseException
	 */
	
	public String getOwner() throws DataBaseException
	{
		String nom=null;
		Connection connect = DBConnection.getInstance();

		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT nom,prenom FROM professeur where owner=1");
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				 nom = result.getString("nom")+" "+result.getString("prenom");
	
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}
		return nom;
	}

	
	/**
	 * Retourner le nom du professeur par son ID
	 * 
	 * @param idProf
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	public String getProfByIdMat(Long idProf) throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		String nomProf=null;

		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM professeur where PROF_ID=?");
			findStatement.setLong(1, idProf);
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
			 nomProf = result.getString("nom")+" "+result.getString("prenom");
				
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}
		return nomProf;

	}
	

}
