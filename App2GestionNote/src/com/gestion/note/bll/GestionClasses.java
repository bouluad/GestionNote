package com.gestion.note.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gestion.note.bo.Classe;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Module;
import com.gestion.note.db.DBConnection;
import com.gestion.note.db.DataBaseException;


/**
 * Cette classe gère les classes et les étudiants  
 */


public class GestionClasses {

	private final static Logger LOG = Logger.getLogger(GestionClasses.class);

	
	/** Objet de GestionClasses du Singleton */
	private static final GestionClasses gestionClasses = new GestionClasses();

	/**
	 * Interdire l'instanciation
	 */
	private GestionClasses() {

	}

	/**
	 * Méthode qui renvoie une référence vers l'objet unique qui existe déjà
	 
	 */
	public static GestionClasses getInstance() {

		return gestionClasses;
	}
	
	/**
	 * Methode retourner ID d'un classe apartir de son nom
	 * 
	 * @param pCode
	 * @return id
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	public Long findid(String pCode) throws DataBaseException,
	ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		
		Long lId;

String lGetClasse = "SELECT * FROM CLASSE WHERE NOM = ?";

try {
	PreparedStatement stmtGetModule = connect
			.prepareStatement(lGetClasse);
	stmtGetModule.clearParameters();
	
	stmtGetModule.setString(1, pCode);

	ResultSet result = stmtGetModule.executeQuery();
	
	if (result.next()) {
		String lCode = result.getString("NOM");
		String lIntitule = result.getString("CODE");
		lId = result.getLong("CLASSE_ID");
		
		
	}

	else {
		throw new ElementNotFoundException("Object with Code=" + pCode
				+ " is not found in database");
	}
} catch (SQLException e) {
	
	throw new DataBaseException(e);
}

return lId;
}


	
	/**
	 * Récupère la liste des classes
	 * 
	 * @return classes
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	
	public List<Classe> getClasses() throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<Classe> lList = new ArrayList<Classe>();

		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM CLASSE");
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String nom = result.getString("NOM");
				String code = result.getString("CODE");
				int id = result.getInt("CLASSE_ID");
				Classe lClasse = new Classe(Long.valueOf(id), nom, code);
				lList.add(lClasse);
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
	 * Récupère la liste des étudiants
	 * 
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Etudiant> getStudents(int pAnnee) throws DataBaseException, ElementNotFoundException {

		List<Etudiant> lList = new ArrayList<Etudiant>();
		Connection connect = DBConnection.getInstance();
		try {

			PreparedStatement findStatement = connect
					.prepareStatement("SELECT * FROM Etudiant E, AnneeEtude A where E.Etudiant_ID=A.Etudiant_ID where A.annee=?");
			findStatement.setInt(1, pAnnee);
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
	 * Récupère la liste des étudiants d'une classe dont l'id est passé comme
	 * paramètre
	 * 
	 * @param pIdClass
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Etudiant> getClasseStudents(int pIdClass, int pAnnee) throws DataBaseException,
			ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<Etudiant> lList = new ArrayList<Etudiant>();

		try {

			PreparedStatement findStatement = connect
					.prepareStatement("SELECT nom,prénom,cne,cin,dateNais,lieuNais, e.Etudiant_ID  FROM "
							+ "anneeetude a,etudiant e where a.Etudiant_id=e.Etudiant_id and annee=? and Classe_id = ?");
			findStatement.setInt(1, pAnnee);
			findStatement.setInt(2, pIdClass);
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
	 * Récupère la liste des étudiants d'une classe dont le nom est passé comme
	 * paramètre
	 * 
	 * @param pClasseName
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public List<Etudiant> getClasseStudents(String pClasseName, int pAnnee) throws DataBaseException,
			ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		List<Etudiant> lList = new ArrayList<Etudiant>();

		try {

			PreparedStatement findStatement = connect
					.prepareStatement("SELECT distinct e.nom,prénom,cne,cin,dateNais,lieuNais,  e.Etudiant_ID,m.intitule  FROM "
							+ "Inscription i,etudiant e, Module m where i.Etudiant_id=e.Etudiant_id  and m.Module_ID=i.Module_ID and i.annee=? and m.intitule = ?");
			findStatement.setInt(1, pAnnee);
			
			findStatement.setString(2, pClasseName);
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
	
	
	public List<Etudiant> getClasseStudents(Long pId) throws DataBaseException,
	ElementNotFoundException {

Connection connect = DBConnection.getInstance();
List<Etudiant> lList = new ArrayList<Etudiant>();

try {

	PreparedStatement findStatement = connect
			.prepareStatement("SELECT distinct e.nom,prénom,cne,cin,dateNais,lieuNais,  e.Etudiant_ID FROM "
					+ "etudiant e where e.Classe_ID = ?");
	
	findStatement.setLong(1, pId);
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
	 * Methode retourner une classe apartir de son id
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	public Classe getClasseById(Long idClasse) throws DataBaseException, ElementNotFoundException {

		Connection connect = DBConnection.getInstance();
		Classe classe =null;
		try {
			PreparedStatement findStatement = connect.prepareStatement("SELECT * FROM CLASSE where Classe_ID=?");
			findStatement.setLong(1, idClasse);
			ResultSet result = findStatement.executeQuery();
			while (result.next()) {
				String nom = result.getString("NOM");
				String code = result.getString("CODE");
				int id = result.getInt("CLASSE_ID");
				classe = new Classe(Long.valueOf(id), nom, code);
			}

		} catch (SQLException e) {
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

		if ( classe == null)
			throw new ElementNotFoundException("No record found in database ");

		return classe;

	}

}

