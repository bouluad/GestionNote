package com.gestion.note.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Cette classe permet de charger les pamètres génrales de configuration de
 * l'application
 * 
 * 29/10/2011
 * 
 * @author Tarik BOUDAA
 * 
 */

public class ConfigurationLoader {

	public static Map<String, String> MAPCONFIG = new HashMap<String, String>();
	public final static String ANNEE_UNIV = "anneeuniv";
	public final static String SEMESTRE = "semestre";
	public final static String SEUILCP = "seuilCp";
	public final static String SEUILCI = "seuilCi";

	public static void load() throws IOException {

		Properties lProp = new Properties();
		lProp.load(new FileInputStream("resources/config/config.properties"));
		MAPCONFIG.put(ANNEE_UNIV, (String) lProp.get("anneeuniv"));
		MAPCONFIG.put(SEMESTRE, (String) lProp.get("semestre"));
		MAPCONFIG.put(SEUILCP, (String) lProp.get("seuilCp"));
		MAPCONFIG.put(SEUILCI, (String) lProp.get("seuilCi"));
	}
}
