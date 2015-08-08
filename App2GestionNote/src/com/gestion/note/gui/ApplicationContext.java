package com.gestion.note.gui;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet de définir un dictionnaire facilitant l'échange et le
 * partage de données entre plusieurs classes
 * 
 * @author BOULUAD
 * 
 */
public class ApplicationContext {

	private static final ApplicationContext instance = new ApplicationContext();

	private static int APPLI_STATE = 0;

	public int vdPercentageCoreLoaded = 0;

	private Map<String, Object> contextStorage = new HashMap<String, Object>();

	private ApplicationContext() {
	}

	public static final ApplicationContext getInstance() {
		return instance;
	}

	public Object getAttribute(String pKey) {
		return contextStorage.get(pKey);
	}

	public synchronized MainFrame getMainFrame() {
		return (MainFrame) contextStorage.get("mainFrame");

	}

	public synchronized void setMainFrame(MainFrame pMainFRame) {
		contextStorage.put("mainFrame", pMainFRame);

	}

	public synchronized void setAttribute(String pKey, Object pObj) {
		contextStorage.put(pKey, pObj);
	}

	public synchronized void setUser(Object pObj) {
		contextStorage.put("user", pObj);
	}

	public synchronized Object getUser() {
		return contextStorage.get("user");
	}

	public void setAppliStarted() {
		APPLI_STATE = 1;
	}

	public synchronized boolean isAppliStarted() {
		if (APPLI_STATE == 1)
			return true;

		return false;
	}

	public void setVdDim(Dimension d) {
		setAttribute("vddimension", d);
	}

	public Dimension getVdDim() {
		return (Dimension) getAttribute("vddimension");
	}

	public int getVdPercentageCoreLoaded() {
		return vdPercentageCoreLoaded;
	}

	public void setVdPercentageCoreLoaded(int pvdPercentageCoreLoaded) {
		vdPercentageCoreLoaded = pvdPercentageCoreLoaded;
	}

}
