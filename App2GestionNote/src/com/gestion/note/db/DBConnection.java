package com.gestion.note.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Cette classe Singeton permet de garntir une seule 
 * connexion à la base de donnees
 * @author Mohammed BOULUAD
 */
public class DBConnection{
	
	/** Logger */
	private final Logger LOG = Logger.getLogger(getClass());
    
	/** Objet de connection*/
	private static Connection connection;
		
	/**
	 * Constructeur privé
	 * @throws DataBaseException 
	 */
	private DBConnection() throws DataBaseException{
		
		 Properties dbProperties = null;

		   // Charger le pilote de la base de données JavaDB
			try {
				
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			} catch (ClassNotFoundException ex) {
				LOG.fatal("Error loading database driver org.apache.derby.jdbc.EmbeddedDriver :"
						+ ex.getMessage());
				throw new DataBaseException("Error loading database driver org.apache.derby.jdbc.EmbeddedDriver ",ex);
			}

		 
		try {
		    dbProperties = DbPropertiesLoader.loadPoperties("Configuration.properties");
	        String dbUrl = dbProperties.getProperty("derby.url") + "gsnoteDB2";
			connection = DriverManager.getConnection(dbUrl, dbProperties);
		} catch (SQLException e) {
		
			LOG.error("Error while trying to connect to database"+e);
			throw new DataBaseException(e);
			
		}
		catch (IOException e) {
			LOG.error("Error I/O while loading parameters of the database");
		    throw new DataBaseException(e);
		}
	}
	
	/**
	 * Méthode permet de retourner une instance de la
	 * connexion s'il existe et la créer sinon
	 * @return connection
	 * @throws DataBaseException 
	 */
	public static Connection getInstance() throws DataBaseException{
		if(connection == null){
			try {
				new DBConnection();
			} catch (DataBaseException e) {
				
				throw new DataBaseException(e);
			}
		}
		return connection;	
	}
	
	public static void main(String[] args) throws DataBaseException {
		DBConnection.getInstance();
	}

}
	
