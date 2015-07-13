package com.gestion.note.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.bind.helpers.ParseConversionEventImpl;

import org.apache.log4j.Logger;

import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Inscription;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.db.DBConnection;
import com.gestion.note.db.DataBaseException;

/**
 * Cette classe gère les modules et les matières
 * 
 * 
 */

public class GestionModules {

	private final static Logger LOG = Logger.getLogger(GestionModules.class);

	/** Objet de GestionNotes du Singleton */
	private static GestionModules gstionModules = null;

	/**
	 * interdire l'instanciation
	 */
	private GestionModules() {

	}

	/**
	 * méthode qui crée une instance uniquement s'il n'en existe pas encore.
	 * Sinon elle renvoie une référence vers l'objet qui existe déjà
	 * 
	 * @return l'unique instance de cette classe
	 * 
	 */
	public static GestionModules getInstance() {
		if (gstionModules == null) {
			gstionModules = new GestionModules();
		}
		return gstionModules;
	}

	
	public Long findid(String pIntitule) throws DataBaseException,
	ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		
		Long lIdModule;
// requete paramétrée permet d'avoir un compte par son numéro
String lGetModule = "SELECT * FROM MODULE WHERE INTITULE = ?";
Module module;
Module lModule = null;
try {
	PreparedStatement stmtGetModule = connect
			.prepareStatement(lGetModule);
	stmtGetModule.clearParameters();
	
	stmtGetModule.setString(1, pIntitule);

	ResultSet result = stmtGetModule.executeQuery();
	
	if (result.next()) {
		String lCode = result.getString("CODE");
		String lIntitule = result.getString("INTITULE");
		String lAnneeMaj = result.getString("version");
		lIdModule = result.getLong("MODULE_ID");
		
		
	}

	else {
		throw new ElementNotFoundException("Object with Code=" + pIntitule
				+ " is not found in database");
	}
} catch (SQLException e) {
	
	throw new DataBaseException(e);
}

return lIdModule;
}

	
	
	
	/**
	 * Récupère la liste de tous les modules
	 * 
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Module> getModules() throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<Module> lList = new ArrayList<Module>();
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM Module");
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String code = result.getString("CODE");
				String intitule = result.getString("INTITULE");
				String version = result.getString("version");
				int id = result.getInt("MODULE_ID");

				Module lModule = new Module(Long.valueOf(id), code, intitule, version);
				lList.add(lModule);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("No record found in database ");

		return lList;

	}

	/**
	 * Récupère la liste des modules d'une classe dont l'id est passé comme
	 * paramètre
	 * 
	 * @param pId
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Module> getClasseModules(Long pId) throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<Module> lList = new ArrayList<Module>();
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM Module where CLASSE_ID=?");
			findStatement.setInt(1, pId.intValue());
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String code = result.getString("CODE");
				String intitule = result.getString("INTITULE");
				String version = result.getString("version");
				int id = result.getInt("MODULE_ID");

				Module lModule = new Module(Long.valueOf(id), code, intitule, version);
				lList.add(lModule);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("No record found in database ");

		return lList;

	}

	/**
	 * Récupère la liste des matières d'un module dont l'id est passé comme
	 * paramètre
	 * 
	 * @param pId
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Matiere> getMatieresModule(Long pId) throws DataBaseException, ElementNotFoundException {
		Connection connect = DBConnection.getInstance();
		List<Matiere> lList = new ArrayList<Matiere>();
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM Matière where MODULE_ID = ?");
			findStatement.setInt(1, pId.intValue());
			
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String intitule = result.getString("INTITULE");
				int nbrHeures = result.getInt("nbrHeure");
				double coeff = result.getDouble("COEFF");
				int id = result.getInt("MATIERE_ID");
				int idProf=result.getInt("PROF_ID");
				Matiere lMat = new Matiere(Long.valueOf(id), intitule, nbrHeures, coeff,Long.valueOf(idProf));
				lList.add(lMat);

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
	 * Récupère la liste des matières
	 * 
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Matiere> getMatieres() throws DataBaseException, ElementNotFoundException {
		Connection connect = DBConnection.getInstance();
		List<Matiere> lList = new ArrayList<Matiere>();
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM Matière");

			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String intitule = result.getString("INTITULE");
				int nbrHeures = result.getInt("nbrHeure");
				double coeff = result.getDouble("COEFF");
				int id = result.getInt("Matiere_ID");
				Matiere lMat = new Matiere(Long.valueOf(id), intitule, nbrHeures, coeff);
				lList.add(lMat);
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
	 * Récuperer une matière  
	 * 
	 * @param pIntitule
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */

	public Matiere getMatiereByName(String pIntitule) throws DataBaseException, ElementNotFoundException {
		
		
		
		Connection connect = DBConnection.getInstance();
		List<Matiere> lList = new ArrayList<Matiere>();
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM Matière where intitule=?");
			findStatement.setString(1, pIntitule);

			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String intitule = result.getString("INTITULE");
				int nbrHeures = result.getInt("nbrHeure");
				double coeff = result.getDouble("COEFF");
				int id = result.getInt("Matiere_ID");
				Matiere lMat = new Matiere(Long.valueOf(id), intitule, nbrHeures, coeff);
				lList.add(lMat);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("No record found in database ");

		return lList.get(0);

	}

	/**
	 * Récupère la liste des modules avec leurs éléments
	 * 
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Module> getModuleWithMatieres() throws DataBaseException, ElementNotFoundException {
		List<Module> lList = getModules();

		for (int i = 0; i < lList.size(); i++) {

			lList.get(i).setMatieres(getMatieresModule(lList.get(i).getId()));

		}

		if (lList.size() == 0)
			throw new ElementNotFoundException("No record found in database ");

		return lList;

	}

	
	/**
	 * Récuperer ID d'un module apartir de son Titre
	 * 
	 * @param pIntitule
	 * @return
	 * @throws DataBaseException
	 */
	
	public  Long getModuleId(String pIntitule) throws DataBaseException
	{  
		Connection connect = DBConnection.getInstance();
		int id=0;
		try {
			
			String RequeteSql = "SELECT MODULE.Module_ID ";
	        RequeteSql +="FROM MODULE   ";
	        RequeteSql +="WHERE MODULE.intitule=?";		
			java.sql.PreparedStatement stmtGetModule = connect.prepareStatement(RequeteSql);
	        stmtGetModule.setString(1,pIntitule);                
	        java.sql.ResultSet result = stmtGetModule.executeQuery();        
	        while (result.next()) {
	        	id = result.getInt("Module_ID");
	        }
	        
	} catch (SQLException e) {

	e.printStackTrace();
	}

	return Long.valueOf(id);
	}

	
	/**
	 * Récuperer un module
	 * 
	 * @param pIntitule
	 * @return
	 * @throws DataBaseException
	 */
	
	public  Module getModule(String pIntitule) throws DataBaseException
	{  
		Connection connect = DBConnection.getInstance();

		Module module= new Module();
		try {
			
			String RequeteSql = "SELECT MODULE.Module_ID,MODULE.code ";
	        RequeteSql +="FROM MODULE   ";
	        RequeteSql +="WHERE MODULE.intitule=?";		
			java.sql.PreparedStatement stmtGetModule = connect.prepareStatement(RequeteSql);
	        stmtGetModule.setString(1,pIntitule);                
	        java.sql.ResultSet result = stmtGetModule.executeQuery();        
	        while (result.next()) {
	        	module.setId(Long.valueOf(result.getInt("Module_ID")));
	        	module.setCode(result.getString("code"));
	        }
	        
	} catch (SQLException e) {

	e.printStackTrace();
	}

	return module;
	}
	
	
	/**
	 * Mettre à jour la moyenne et validation dans la table 
	 * Inscription 
	 * 
	 * @param idEtd
	 * @param idMod
	 * @param moyenne
	 * @param valide
	 * @throws DataBaseException
	 */
	public void updateMoyenneModule(Long idEtd,Long idMod,Double moyenne,String valide) throws DataBaseException
	{
		Connection connect = DBConnection.getInstance();
		String lUpdateNote = "UPDATE  Inscription" + " SET moyenne =?,validation=? " + "where Etudiant_ID=? and Module_ID=?";

		PreparedStatement lUpdateNoteStatement;
		try {
			lUpdateNoteStatement = connect.prepareStatement(lUpdateNote);
			lUpdateNoteStatement.setDouble(1,moyenne);
			lUpdateNoteStatement.setString(2, valide);
			lUpdateNoteStatement.setLong(3,idEtd);
			lUpdateNoteStatement.setLong(4,idMod);
			lUpdateNoteStatement.executeUpdate();
			
		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}
	}
	
	
	/**
	 * Récuperer la liste des Etudiants inscrit dans un module
	 * 
	 * @param idModule
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	public List<Etudiant> getStudentsByIdModule(Long idModule) throws DataBaseException,
	ElementNotFoundException {

	Connection connect = DBConnection.getInstance();
	List<Etudiant> lList = new ArrayList<Etudiant>();
	
	try {
	
		PreparedStatement findStatement = connect
				.prepareStatement("SELECT *  FROM "
						+ "Inscription i,etudiant e, Module m where i.Etudiant_id=e.Etudiant_id  and m.Module_ID=i.Module_ID  and m.Module_ID = ?");
		findStatement.setLong(1, idModule);
		ResultSet result = findStatement.executeQuery();
		while (result.next()) {
			String nom = result.getString("NOM");
			String prenom = result.getString("prénom");
			String cin = result.getString("cin");
			String cne = result.getString("cne");
			Date dateNais = result.getDate("dateNais");
			String lieuNais = result.getString("lieuNais");
			int id = result.getInt("Etudiant_ID");
			Etudiant lStd = new Etudiant(Long.valueOf(id), nom, prenom, cne, cin, dateNais, lieuNais);
			lList.add(lStd);
		}
	
	} catch (SQLException e) {
		LOG.error("Erreur lors d'une opération sur la base de données :" + e);
		throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
	
	}
	
	if (lList.size() == 0)
		throw new ElementNotFoundException("No record found in database ");
	
	return lList;
	
	}
	
	
	/**
	 * Methode pour récuperer la moyenne et la validation d'un etudiant inscrit 
	 * dans un module
	 * 
	 * @param idMod
	 * @param idEt
	 * @return
	 * @throws DataBaseException
	 */
	
	public  Inscription getModuleInsctiption(Long idMod,Long idEt) throws DataBaseException
	{  
		Connection connect = DBConnection.getInstance();
		Inscription inscription =new Inscription();
		try {
			
			String RequeteSql = "SELECT * ";
	        RequeteSql +="FROM Inscription,Module,Etudiant ";
	        RequeteSql +="WHERE MODULE.Module_ID=? and MODULE.Module_ID=Inscription.Module_ID and"
	        +" Etudiant.Etudiant_ID=Inscription.Etudiant_ID and Etudiant.Etudiant_ID=?";		
			java.sql.PreparedStatement stmtGetModule = connect.prepareStatement(RequeteSql);
	        stmtGetModule.setLong(1,idMod);
	        stmtGetModule.setLong(2,idEt);                
	        java.sql.ResultSet result = stmtGetModule.executeQuery();        
	        while (result.next()) {
	        	inscription.setValidation(result.getString("validation"));
	        	inscription.setMoyenne(result.getDouble("moyenne"));
	        }
	        
	} catch (SQLException e) {

	e.printStackTrace();
	}

	return inscription;
	}	
}
