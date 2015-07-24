package com.gestion.note.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GenerateXml;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionNotes;
import com.gestion.note.bll.GestionProf;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.bo.Note;
import com.gestion.note.config.ConfigurationLoader;
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

		DialogChoixMatiere dialog = new DialogChoixMatiere();
		dialog.setVisible(true);

		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		mainFrame.setDialogMatiereSlection(dialog);

		List<Etudiant> list = dialog.getListStudents();
		TableStudentsPanel tableStudentsPanel = mainFrame.getTableStudentsPanel();
		
		mainFrame.addToCenterPanel(mainFrame.getPanel(),tableStudentsPanel);
		mainFrame.setTxtLabel( "Matière : " +(String) dialog.getComboListMatieres().getCombo().getSelectedItem());

		TableStudentModel model = tableStudentsPanel.getModel();

		model.updae(list, (String) dialog.getComboListMatieres().getCombo().getSelectedItem(),dialog.getRadiostat());

	}
	

	
	public void openSaisie() throws DataBaseException
	{
		System.out.println("open");
		
		DialogChoixMatiere2 dialog = new DialogChoixMatiere2();
		dialog.setVisible(true);

		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		mainFrame.setDialogMatiereSlection2(dialog);

		List<Etudiant> list = dialog.getListStudents();
		TableStudentsPanel2 tableStudentsPanel2;
		try {
			tableStudentsPanel2 = new TableStudentsPanel2(getNumberOfMatier((String) dialog.getComboListModules().getCombo().getSelectedItem()));
			/*
			 * ajouter au centre du MainFrame
			 */
			mainFrame.addToCenterPanel(mainFrame.getPanel(),tableStudentsPanel2);
			mainFrame.setTxtLabel("Module : " +(String) dialog.getComboListModules().getCombo().getSelectedItem());

			
			TableStudentModel2 model = tableStudentsPanel2.getModel();
			model.update(list, (String) dialog.getComboListModules().getCombo().getSelectedItem(),(String) dialog.getComboListClasses().getCombo().getSelectedItem());
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

	/**
	 * récupèrer la liste des matières d'un module
	 * 
	 * @param moduleName
	 * @return
	 * @throws DataBaseException
	 * @throws ElementNotFoundException
	 */
	private List<String> getNumberOfMatier(String moduleName) throws DataBaseException, ElementNotFoundException{
		List<String> matieres=new ArrayList<String>(); 
		Long id = Long.valueOf(0);
		id = GestionModules.getInstance().getModuleId(moduleName);
		GestionModules lGestModules  = GestionModules.getInstance();	
		List<Matiere> listMatieres = lGestModules.getMatieresModule(id);

		for(int j=0;j<listMatieres.size();j++){
			matieres.add(listMatieres.get(j).getIntitule());
		}
		return matieres;
	}
	
	public void help(){
		DialogueImprimer imp=new DialogueImprimer();
		imp.setVisible(true);
		
	}
	

	public void save() {
	
		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		DialogChoixMatiere dialog = mainFrame.getDialogMatiereSlection();
		
		
		try {

			mainFrame.getTableStudentsPanel().saveChanges(
					(String) dialog.getComboListMatieres().getCombo().getSelectedItem());
			
			JOptionPane.showMessageDialog(null, "Les notes sont sauvegardé",
					"Modification", JOptionPane.INFORMATION_MESSAGE);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void config() throws ElementNotFoundException
	{
		try {
			new DialogConfiguration();
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	mainFrame.getTableStudentsPanel().printTable();
	}
	
/**
 * methode de générer le fichier XML
 * @throws DataBaseException 
 */
	public void generate() throws DataBaseException {
		
		
		//Recuperer le nom du module
		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		String moduleName=mainFrame.getTextLblHeader();
				
		GenerateXml.generateXmlFile(moduleName);
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
