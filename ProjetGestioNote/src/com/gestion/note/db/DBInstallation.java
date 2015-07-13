package com.gestion.note.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 * Cette classe permet d'initaliser la base de données embaqruée
 * 
 * 01/10/2011
 * 
 * @author Tarik BOUDAA email tarikboudaa@yahoo.fr
 * 
 */
public class DBInstallation {

	/** Logger */
	private final Logger LOG = Logger.getLogger(getClass());

	/** permet de stocker les proprietés de la base de données */
	private Properties dbProperties = null;

	private boolean isConnected;

	private String dbName;

	/**
	 * Constructeur, charge les paramètres de la base de données et elle
	 * 
	 * 
	 * @throws DataBaseException
	 */
	public DBInstallation() throws DataBaseException {
		try {
			dbProperties = DbPropertiesLoader.loadPoperties("Configuration.properties");
		} catch (IOException e) {
			LOG.error("Error I/O while loading parameters of the database");
			throw new DataBaseException("Error I/O while loading parameters of the database", e);
		}

		// Charger les paramètres de la base à partir du ficher de configuration
		dbName = dbProperties.getProperty("db.name");
		String dirver = dbProperties.getProperty("db.driver");

		// Specifier le repertoire d'installation de la base de données
		setApplicationHome();

		// Charger le pilote de la base de données JavaDB
		try {

			Class.forName(dirver);

		} catch (ClassNotFoundException ex) {
			LOG.fatal("Error loading database driver org.apache.derby.jdbc.EmbeddedDriver :" + ex);
			throw new DataBaseException("Error loading database driver :" + dirver, ex);
		}
	}

	/**
	 * Permet d'initialiser la base de données
	 */

	public void installDB() throws DataBaseException {

		// si la base n'existe pas alors créer la.
		if (!dbExists()) {
			LOG.info("Starting the inialisation of the Database");
			try {
				createDatabase();

			} catch (DataBaseException ex) {
				// clean de la base
				disconnect();
				cleanDb();
				LOG.fatal("Error while creating database due to :" + ex);
				throw new DataBaseException("Error while creating database", ex);
			}

			LOG.info("The initialization of the database is completed successfully");
		}
	}

	/**
	 * Permet de spécifier le répertoire home de l'application
	 */
	private void setApplicationHome() {

		String userHomeDir = System.getProperty("user.home", ".");
		String systemDir = userHomeDir + "/.gsnotes";

		// Installer la base de donnees dans <userhome>/.gsnotes/
		System.setProperty("derby.system.home", systemDir);

		// Creer le répertoire d'installation de la base de données
		File fileSystemDir = new File(systemDir);
		fileSystemDir.mkdir();
	}

	private String getApplicationHome() {

		return System.getProperty("derby.system.home");
	}

	/**
	 * Teste si la base est dejà installée
	 */
	private boolean dbExists() {
		boolean exists = false;
		String dbLocation = getDatabaseLocation();
		File dbFileDir = new File(dbLocation);
		if (dbFileDir.exists()) {
			exists = true;
		}
		return exists;
	}

	/**
	 * Cette méthode fournit le chemin où la base de données est installée
	 * 
	 * @return le chemin de la base
	 */
	private String getDatabaseLocation() {
		String dbLocation = System.getProperty("derby.system.home") + "/" + "gsnoteDB";
		return dbLocation;
	}

	/**
	 * Cette méthode permet de créer la base de données
	 * 
	 * @throws DataBaseException
	 * 
	 * @throws DataBaseException
	 * @throws Exception
	 */
	private void createDatabase() throws DataBaseException {

		try {
			// Indique que l'on veut créer la base de données
			dbProperties.put("create", "true");
			String dbUrl = getDatabaseUrl();
			DriverManager.getConnection(dbUrl, dbProperties);
			executeSqlScript("resources/data/sql.data");

		} catch (Throwable ex) {

			throw new DataBaseException("Error while creating database", ex);
		} finally {
			dbProperties.remove("create");
		}
	}

	/**
	 * L'Api JAVA de base ne permet pas de supprimer un répertoire contenant des
	 * fichiers, il faut donc supprimer tous les fichiers du répertoire
	 * manuellement cette méthode supprime récursivement le répertoire et son
	 * contenu
	 * 
	 * @param path
	 * @return
	 */
	private boolean deleteDirectory(File path) {
		boolean resultat = true;

		if (path.exists()) {
			File[] files = path.listFiles();

			for (int i = 0; i < files.length; i++) {

				if (files[i].isDirectory()) {
					resultat &= deleteDirectory(files[i]);
				} else {
					resultat &= files[i].delete();
				}
			}
		}
		resultat &= path.delete();
		return (resultat);
	}

	/**
	 * Cette méthode permet d'executer un Scripte SQL
	 * 
	 * @param pFile
	 * @throws DataBaseException
	 */
	private void executeSqlScript(String pFile) throws DataBaseException {

		String lLinge = "";
		int ignoredLines = 0;
		StringBuffer sb = new StringBuffer();

		try {
			FileReader fr = new FileReader(new File(pFile));

			BufferedReader br = new BufferedReader(fr);
			// ignorer les commentaires et les lignes vides
			while ((lLinge = br.readLine()) != null) {

				if (lLinge.trim().equals("") || lLinge.trim().startsWith("-") || lLinge.trim().startsWith("/*")) {
					ignoredLines++;
				} else {
					sb.append(lLinge);
				}

			}
			br.close();

			String[] lInstructions = sb.toString().split(";");

			Connection connect = DBConnection.getInstance();
			Statement st = connect.createStatement();

			for (int i = 0; i < lInstructions.length; i++) {
				st.executeUpdate(lInstructions[i]);
				LOG.debug(">>" + lInstructions[i]);
			}

		} catch (Exception e) {
			LOG.fatal("Error while crating database :" + e);
			throw new DataBaseException("Error while crating database", e);

		}
		LOG.info("There is " + ignoredLines + " lines ignored");
		LOG.info("SQL Script Executed successfully");

	}

	public String getDatabaseUrl() {
		String dbUrl = dbProperties.getProperty("derby.url") + dbName;
		return dbUrl;
	}

	private void cleanDb() {
		File fileSystemDir = new File(getApplicationHome());
		deleteDirectory(fileSystemDir);
	}

	public void disconnect() {

		String dbUrl = getDatabaseUrl();
		dbProperties.put("shutdown", "true");
		try {
			DriverManager.getConnection(dbUrl, dbProperties);

		} catch (SQLException ex) {

			LOG.error("Error while disconnect : " + ex);
		}

	}

}
