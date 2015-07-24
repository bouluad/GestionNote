package com.gestion.note.config;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bo.Module;
import com.gestion.note.bo.parametre;
import com.gestion.note.db.DBConnection;
import com.gestion.note.db.DataBaseException;



/**
 * Cette classe permet de charger les pamètres génrales de configuration de
 * l'application
 * @author BOULUAD
 *
 */

public class Configuration {
	
	
	/**LOG **/
	private final static Logger LOG = Logger.getLogger(Configuration.class);

	
	/** Objet de GestionClasses du Singleton */
	private static final Configuration configuration = new Configuration();

	/**
	 * Interdire l'instanciation
	 */

	private  Configuration() {
	}
	
	
	public static Configuration getInstance() {

		return configuration;
	}

	
	
	public void update(int annee,int sem,int seuilcp,int seuici) throws DataBaseException, SQLException
	{
		
		
		Connection connect = DBConnection.getInstance();
		String lUpdate = "UPDATE  Configuration" + " SET anneeuniv =?,semestre=?,seuilCp=?,seuilCi=?";
	
		PreparedStatement lUpdateStatement;
		
		try {
						
			lUpdateStatement = connect.prepareStatement(lUpdate);
			lUpdateStatement.setInt(1,annee);
			lUpdateStatement.setInt(2,sem);
			lUpdateStatement.setInt(3,seuilcp);
			lUpdateStatement.setInt(4,seuici);
			lUpdateStatement.executeUpdate();
			
		} catch (SQLException e) {

			LOG.error("Erreur lors d'une opération sur la base de données :" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);

		}

	}
	
	public parametre getPropertie() throws DataBaseException,ElementNotFoundException
	{
		
		
		Connection connect = DBConnection.getInstance();
		
		parametre param=new parametre();
		
		String lReq = "SELECT * FROM Configuration";
	
try {
	PreparedStatement stmt = connect
			.prepareStatement(lReq);

	ResultSet result = stmt.executeQuery();
	
	if (result.next()) {
		param.setAnneeuniv(result.getInt("anneeuniv"));
		param.setSemestre(result.getInt("semestre"));
		param.setSeuilCi(result.getInt("seuilCi"));
		param.setSeuilCp(result.getInt("seuilCp"));	
		
	}

	
} catch (SQLException e) {
	
	throw new DataBaseException(e);
}

return param ;

	}

}
