package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.db.DataBaseException;


/**
 * Cette classe définie les actions d'un menu
 */


public class MenuActions extends AbstractAction {

    /**le nom de la méthide à éxecuter */
	private String method;


	/**le menu */
	private AsnMenu menu;

	
	
	
	/**constructeur */
	
	public MenuActions(String label, String pMethod, char pShortcut) {
		super(label);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("CTRL " + pShortcut));
		method = pMethod;
	}

	/**
	 * Constructeur
	 */
	public MenuActions(AsnMenu pMenu, String label, String pMethod, char pShortcut) {
		super(label);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("CTRL " + pShortcut));
		method = pMethod;
		menu = pMenu;
	}

	/**
	 * Constructeur
	 * 
	 * @param label
	 *            le label
	 * @param pMethod
	 *            la méthode
	 * @param pShortcut
	 *            le raccourci
	 */
	public MenuActions(AsnMenu pMenu, String label, String pMethod, char pShortcut, String pIconName) {
		super(label);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("CTRL " + pShortcut));
		method = pMethod;
		menu = pMenu;
	}

	/**
	 * Constructeur
	 * 
	 * @param label
	 *            le label
	 * @param pMethod
	 *            la méthode
	 * @param pIconName
	 *            le raccourci
	 */
	public MenuActions(String label, String pMethod, String pIconName) {
		super(label);
		if (pIconName != null) {
			putValue(Action.SMALL_ICON, new ImageIcon(pIconName));
		} else {

		}
		method = pMethod;
	}

	public MenuActions(AsnMenu pMenu,String pMethod, String pIconName) {

		if (pIconName != null) {
			putValue(Action.SMALL_ICON, new ImageIcon(pIconName));
		}

		method = pMethod;
		menu= pMenu;
	}

	public void actionPerformed(ActionEvent ev) {

		try {
			this.getClass().getDeclaredMethod(method).invoke(this);
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public void newSaisie() throws DataBaseException {

		
	}
	

	
	public void openSaisie() throws DataBaseException, ElementNotFoundException
	{
		DialogChoixClasse dialog = new DialogChoixClasse();
		dialog.setVisible(true);
		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		
		mainFrame.getPanel().setLayout(new BorderLayout());
		mainFrame.setDialogChoixClasse(dialog);
		
		PanelDeliberation panelDeleberation;
		panelDeleberation = new PanelDeliberation(dialog.getIdClasseChoisi());
		
		ModelsPanel modelsPanel;
		modelsPanel = new ModelsPanel(dialog.getIdClasseChoisi());
		
		
		/*
		 * ajouter au centre du MainFrame
		 */
		mainFrame.addToCenter(mainFrame.getPanel(),panelDeleberation,modelsPanel);
		
		//mainFrame.addToCenter(mainFrame.getPanel(),modelsPanel);
		
		mainFrame.setTxtLabel("Classe : " +(String) dialog.getComboListClasses().getCombo().getSelectedItem());

		List<Etudiant> list = dialog.getListStudents();
		ModelTableDeliberation model = panelDeleberation.getModel();
		model.update(list,dialog.getIdClasseChoisi());
		
	}

	/**
	 * récupèrer la liste des matières d'un module
	 * 
	 * @param moduleName
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	private List<String> getNumberOfMatier(String moduleName){
		
		return new ArrayList();
	}
	
	public void help(){
		DialogueImprimer imp=new DialogueImprimer();
		imp.setVisible(true);
		
	}
	

	public void save() {
	
		
	}
	public void config() 
	{
		
	}
    
	public void exit()
	{
		int returnedValue = new JOptionPane()
		.showConfirmDialog(null,
			"Vous êtes sur le point de fermer l'application. Voulez-vous vraiment continuer ?");
	if (returnedValue == JOptionPane.OK_OPTION)
	    System.exit(0);
	}
	
	
	public void print()
	{
	MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
	//mainFrame.getTableStudentsPanel().printTable();
	}
	
/**
 * methode de générer le fichier XML
 * @throws DataBaseException 
 */
	public void generate() throws DataBaseException {
		
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
