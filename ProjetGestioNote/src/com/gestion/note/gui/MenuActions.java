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
		mainFrame.setTxtLabel( "Matière   :  " +(String) dialog.getComboListMatieres().getCombo().getSelectedItem());

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
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void config()
	{
		try {
			new DialogChoixProf();
			
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
 */
	public void generate() {
		
		
		//Recuperer le nom du module
		MainFrame mainFrame = (MainFrame) menu.getMenuContainer();
		String moduleName=mainFrame.getTextLblHeader();
		
		String[] moduleNameTab=moduleName.split(" : ");
		
		if(moduleName.equals("")|| !moduleNameTab[0].equals("Module")){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Veuillez consulter un module !!","Avertissement!!", JOptionPane.WARNING_MESSAGE);
		}
		else{
		//choix le chemin 
		
		String chemin="";
		
		// permet de choisir l'emplaçement de stockage du fichier 
        
		final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
     
             int val_retour = fc.showSaveDialog(null);

             if (val_retour == JFileChooser.APPROVE_OPTION) {
                File fichier = fc.getSelectedFile();
              
                chemin=fichier.getAbsolutePath();
              
             } 
		
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		GestionModules gsModules = GestionModules.getInstance();
		GestionProf gsprof=GestionProf.getInstance();
		GestionNotes gsNotes=GestionNotes.getInstance();
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("listeNotes");
			doc.appendChild(rootElement);
			
			// module element
			Element module = doc.createElement("module");
			rootElement.appendChild(module);
			

			// nom du module
			Element  nom= doc.createElement("nom");
			nom.appendChild(doc.createTextNode(moduleNameTab[1]));
			module.appendChild(nom);
			
			// code du module
			Module mod=gsModules.getModule(moduleNameTab[1]);
			
			Element  code= doc.createElement("code");
			code.appendChild(doc.createTextNode(mod.getCode()));
			module.appendChild(code);
		
			Element  classe= doc.createElement("classe");
			classe.appendChild(doc.createTextNode(mainFrame.getDialogMatiereSlection2().getComboListClasses().getCombo().getSelectedItem().toString()));
			module.appendChild(classe);
			
			
			List<Matiere> listMat=gsModules.getMatieresModule(mod.getId());
			
			Element matiers=doc.createElement("matieres");
			for (int i = 0; i < listMat.size(); i++) {
				Element element=doc.createElement("matiere");
				matiers.appendChild(element);
				
				Attr attr = doc.createAttribute("id");
				attr.setValue(listMat.get(i).getId().toString());
				element.setAttributeNode(attr);
				
				Attr attr1 = doc.createAttribute("titre");
				attr1.setValue(listMat.get(i).getIntitule());
				element.setAttributeNode(attr1);
			
				Attr attr2 = doc.createAttribute("enseignant");
				attr2.setValue(gsprof.getProfByIdMat(listMat.get(i).getIdProf()));
				element.setAttributeNode(attr2);
			}
			
			module.appendChild(matiers);
			
			// les notes d'un module
			Element notes = doc.createElement("notes");
			rootElement.appendChild(notes);
			
			List <Etudiant> etudiants=gsModules.getStudentsByIdModule(mod.getId());
			for(int i=0;i<etudiants.size();i++){
			
				Element etudiant = doc.createElement("etudiant");
				notes.appendChild(etudiant);
				
				Element cne = doc.createElement("cne");
				cne.appendChild(doc.createTextNode(etudiants.get(i).getCne()));
				etudiant.appendChild(cne);
				
				Element nomEtudiant = doc.createElement("nom");
				nomEtudiant.appendChild(doc.createTextNode(etudiants.get(i).getNom()));
				etudiant.appendChild(nomEtudiant);
				
				Element prenomEtudiant = doc.createElement("prenom");
				prenomEtudiant.appendChild(doc.createTextNode(etudiants.get(i).getPrenom()));
				etudiant.appendChild(prenomEtudiant);
				
				
				for (int j = 0; j < listMat.size(); j++) {

					Element matiere = doc.createElement("matiere");
					etudiant.appendChild(matiere);
					
					Attr attr = doc.createAttribute("id");
					attr.setValue(listMat.get(j).getId().toString());
					matiere.setAttributeNode(attr);
				
					Note note=gsNotes.getNote(listMat.get(j).getId().intValue(),etudiants.get(i).getId().intValue(), Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)));
					
					Element noteSn = doc.createElement("noteSN");
					noteSn.appendChild(doc.createTextNode(String.valueOf(note.getNoteSN())));
					matiere.appendChild(noteSn);

					Element noteSr = doc.createElement("noteSR");
					noteSr.appendChild(doc.createTextNode(String.valueOf(note.getNoteSR())));
					matiere.appendChild(noteSr);
						
				}
				
				Element moyenne = doc.createElement("moyenne");
				moyenne.appendChild(doc.createTextNode(String.valueOf(gsModules.getModuleInsctiption(mod.getId(),etudiants.get(i).getId()).getMoyenne())));
				etudiant.appendChild(moyenne);
				
				Element validation = doc.createElement("validation");
				validation.appendChild(doc.createTextNode(String.valueOf(gsModules.getModuleInsctiption(mod.getId(),etudiants.get(i).getId()).getValidation())));
				etudiant.appendChild(validation);
			}
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(chemin+"\\ListeNotes_"+moduleNameTab[1]+".xml"));
	 
			transformer.transform(source, result);

			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Le fichier a été généré avec succès","Message!!", JOptionPane.INFORMATION_MESSAGE);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataBaseException e) {
			System.out.println("Element not found1");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println("Element not found2");
			e.printStackTrace();
		}
		}
		
		
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
