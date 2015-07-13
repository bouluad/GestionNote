
package com.gestion.note.gui;


import java.awt.Container;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 * Cette classe permet de construire un menu
 */
public class AsnMenu {

	/** Barre d'outils */
	private JToolBar toolBar = new JToolBar();

	/** Barre de menu */
	private JMenuBar menuBar = new JMenuBar();

	/** Menu Fichier */
	private JMenu fileMenu = new JMenu("Fichier");

	/** Menu Livraison */
	private JMenu livraisonMenu = new JMenu("Livraison");

	/** Menu Aide */
	private JMenu helpMenu = new JMenu("Aide");

	/** Menu configuration */
	private JMenu configMenu = new JMenu("Configuration");

	/** Le conteneur du menu */
	
	
	/** container est composant comme button ...
     est composé de panel et window*/
	private Container menuContainer;

	/**
	 * Constructeur
	           le contenur
	 */
	public AsnMenu(Container pC) {

		menuContainer = pC;
		buildMenu();

	}

	/**
	 * Constructeur par défaut
	 */
	public AsnMenu() {
		buildMenu();

	}

	/**
	 * Méthode qui permet de construire le menu
	 */
	private void buildMenu() {

		// Création des actions

		MenuActions newAction = new MenuActions(this, "Nouveau", "newSaisie", 'N');
		MenuActions openAction = new MenuActions(this, "Ouvrir", "openSaisie", 'O');
		MenuActions saveAction = new MenuActions(this, "Enregistrer", "save", 'S');
		MenuActions printAction = new MenuActions(this, "Imprimer", "print", 'P');
		MenuActions generateAction = new MenuActions(this, "Génerer", "generate", 'G');
		MenuActions quiterAction = new MenuActions(this, "Quitter", "exit", 'X');
		MenuActions helpAction = new MenuActions(this, "help-Content", "help", 'H');
		MenuActions configAction = new MenuActions(this, "config", "config", 'C');

		
		// Association des actions aux menus
		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		fileMenu.add(quiterAction);

		livraisonMenu.add(printAction);
		livraisonMenu.add(generateAction);
		
        helpMenu.add(helpAction);
        configMenu.add(configAction);
		// Ajout des menus dans la barre de menu

		menuBar.add(fileMenu);
		menuBar.add(livraisonMenu);
		menuBar.add(configMenu);
		menuBar.add(configMenu);
		menuBar.add(helpMenu);

		// Créarion de la barre d'outils
		String iconDirectory = "resources/icons/";
		toolBar.add(new MenuActions(this,"newSaisie", iconDirectory + "insert.png"));
		toolBar.add(new MenuActions(this,"openSaisie", iconDirectory + "list.png"));
		toolBar.add(new MenuActions(this,"save", iconDirectory + "save1.png"));
		toolBar.add(new MenuActions(this,"generate", iconDirectory + "xml.png"));
		toolBar.add(new MenuActions(this,"print", iconDirectory + "print2.png"));
		toolBar.add(new MenuActions(this,"config", iconDirectory + "config.png"));
		toolBar.add(new MenuActions(this,"help", iconDirectory + "help1.png"));
	}

	public JToolBar getToolBar() {

		return toolBar;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public Container getMenuContainer() {
		return menuContainer;
	}

	public void setMenuContainer(Container menuContainer) {
		this.menuContainer = menuContainer;
	}

}
