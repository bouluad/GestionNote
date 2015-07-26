package com.gestion.note.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.bo.Note;
import com.gestion.note.config.Configuration;
import com.gestion.note.db.DataBaseException;

/**
 * Génération des notes d'un module ou d'une matière sous format xml
 * 
 *
 */
public class GenerateXml {
	
	/**LOG**/
	
	private final static Logger LOG = Logger.getLogger(GenerateXml.class);
	
	private static GestionModules gsModules = GestionModules.getInstance();
	private static GestionProf gsprof=GestionProf.getInstance();
	private static GestionNotes gsNotes=GestionNotes.getInstance();
	private static GestionClasses gsClasse=GestionClasses.getInstance();
	
	/**
	 * methode permet de générer le fichier XML
	 * @param name 
	 * @throws DataBaseException 
	 */
	public static void generateXmlFile(String name) throws DataBaseException{
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		/*
		 * séparation du nom du module ou du matière et le préfixe "Module" ou "Matière"
		 * exmple : Module : Programation c++ 
		 */
		String[] nameTab=name.split(" : ");
		
		//si l'utilisateur consulte un module
		if(nameTab[0].equals("Module")){
		//choix le chemin 
		String chemin =choixChemin();
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("listeNotes");
			doc.appendChild(rootElement);
			
			//definir le type de la liste (matiere ou module)
			Attr typeAttr = doc.createAttribute("type");
			typeAttr.setValue("module");
			rootElement.setAttributeNode(typeAttr);
			
			
			// module element
			Element module = doc.createElement("module");
			rootElement.appendChild(module);
			

			// nom du module
			Element  nom= doc.createElement("nom");
			nom.appendChild(doc.createTextNode(nameTab[1]));
			module.appendChild(nom);
			
			// code du module
			Module mod=gsModules.getModule(nameTab[1]);
			
			Element  code= doc.createElement("code");
			code.appendChild(doc.createTextNode(mod.getCode()));
			module.appendChild(code);
		
			//classe du module
			Element  classe= doc.createElement("classe");
			classe.appendChild(doc.createTextNode(gsClasse.getClasseById(mod.getIdClasse()).getNom()));
			module.appendChild(classe);
			
			//liste des matière d'un module
			List<Matiere> listMat=gsModules.getMatieresModule(mod.getId());
			Element matiers=doc.createElement("matieres");
			for (int i = 0; i < listMat.size(); i++){
				matieresTag(doc, listMat.get(i),matiers);	
			}			
			module.appendChild(matiers);
			
			
			List<Etudiant> etudiants = gsModules.getStudentsByIdModule(mod.getId());
			// les notes d'un module
			Element notes = doc.createElement("notes");
			rootElement.appendChild(notes);
			for(int i=0;i<etudiants.size();i++){
				notesTag(doc, notes,etudiants.get(i), mod, listMat);
			}
			
			
			transformer(doc, chemin,nameTab[1]);

		} catch (ParserConfigurationException e) {
			LOG.error("Erreur lors de la génération du fichier XML" + e);
			
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);

		} catch (DataBaseException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
		
		} catch (ElementNotFoundException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
		} catch (TransformerConfigurationException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors de la génération du fichier XML" + e);
		} catch (TransformerException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors de la génération du fichier XML" + e);
		}
		}
		//si l'utilisateur consulte une matière
		else if(nameTab[0].equals("Matière")){
			//choix le chemin 
			String chemin =choixChemin();
			
			try {
				
				docBuilder = docFactory.newDocumentBuilder();
				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("listeNotes");
				doc.appendChild(rootElement);
				
				//definir le type de la liste (matiere ou module)
				Attr typeAttr = doc.createAttribute("type");
				typeAttr.setValue("matiere");
				rootElement.setAttributeNode(typeAttr);
				
				// module element
				Element module = doc.createElement("module");
				rootElement.appendChild(module);
			
				// nom du module
				Matiere mat=gsModules.getMatiereByName(nameTab[1]);
				Module mod=gsModules.getModuleById(mat.getId());
				
				Element  nom= doc.createElement("nom");
				nom.appendChild(doc.createTextNode(mod.getIntitule()));
				module.appendChild(nom);			
				
				//code du module
				Element  code= doc.createElement("code");
				code.appendChild(doc.createTextNode(mod.getCode()));
				module.appendChild(code);
			
				//classe du module
				Element  classe= doc.createElement("classe");
				classe.appendChild(doc.createTextNode(gsClasse.getClasseById(mod.getIdClasse()).getNom()));
				module.appendChild(classe);
				
				//la matière du module
				Element matiers=doc.createElement("matieres");
				matieresTag(doc,mat,matiers);
				module.appendChild(matiers);

				List<Etudiant> etudiants = gsModules.getStudentsByIdModule(mod.getId());
				
				// les notes d'un module
				Element notes = doc.createElement("notes");
				rootElement.appendChild(notes);
				List<Matiere> listMat=new ArrayList<Matiere>();
				listMat.add(mat);
				for(int i=0;i<etudiants.size();i++){
					notesTag(doc, notes,etudiants.get(i), mod,listMat);
				}
				
				transformer(doc, chemin,nameTab[1]);
				
				
			} catch (ParserConfigurationException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors de la génération du fichier XML" + e);

			} catch (DataBaseException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors d'une opération sur la base de données" + e);
				throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
			} catch (ElementNotFoundException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors d'une opération sur la base de données" + e);
				throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
			} catch (TransformerConfigurationException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors de la génération du fichier XML" + e);
			} catch (TransformerException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors de la génération du fichier XML" + e);
			}
			}
			else{
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Veuillez consulter un module !!","Avertissement!!", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Ajouter une balise matière à la balise matières 
	 * @param doc
	 * @param matiere
	 * @param matiers
	 * @throws DataBaseException 
	 */
	private static void matieresTag(Document doc,Matiere matiere,Element matiers) throws DataBaseException{
		
			try {
			Element element=doc.createElement("matiere");
			matiers.appendChild(element);
			
			Attr attr = doc.createAttribute("id");
			attr.setValue(matiere.getId().toString());
			element.setAttributeNode(attr);
			
			Attr attr1 = doc.createAttribute("titre");
			attr1.setValue(matiere.getIntitule());
			element.setAttributeNode(attr1);
		
			Attr attr2 = doc.createAttribute("enseignant");
			
			attr2.setValue(gsprof.getProfByIdMat(matiere.getIdProf()));
			element.setAttributeNode(attr2);

			} catch (DOMException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors de la génération du fichier XML" + e);
			} catch (DataBaseException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors d'une opération sur la base de données" + e);
				throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
			} catch (ElementNotFoundException e) {
				new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
				LOG.error("Erreur lors d'une opération sur la base de données" + e);
				throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
			}
		
	}
	
	/**
	 * Ajouter les notes d'un etudiant
	 * @param doc
	 * @param notes
	 * @param et
	 * @param mod
	 * @param listMat
	 */
	private static void notesTag(Document doc,Element notes,Etudiant et,Module mod,List<Matiere> listMat){

		try {

					
		Element etudiant = doc.createElement("etudiant");
		notes.appendChild(etudiant);
						
		Element cne = doc.createElement("cne");
		cne.appendChild(doc.createTextNode(et.getCne()));
		etudiant.appendChild(cne);
						
		Element nomEtudiant = doc.createElement("nom");
		nomEtudiant.appendChild(doc.createTextNode(et.getNom()));
		etudiant.appendChild(nomEtudiant);
						
		Element prenomEtudiant = doc.createElement("prenom");
		prenomEtudiant.appendChild(doc.createTextNode(et.getPrenom()));
		etudiant.appendChild(prenomEtudiant);
						
						
		for (int j = 0; j < listMat.size(); j++) {
			matiereNoteTag(doc,etudiant, listMat.get(j), et);
		}
		if(listMat.size()!=1){				
		Element moyenne = doc.createElement("moyenne");
		moyenne.appendChild(doc.createTextNode(String.valueOf(gsModules.getModuleInsctiption(mod.getId(),et.getId()).getMoyenne())));
		etudiant.appendChild(moyenne);
						
		Element validation = doc.createElement("validation");
		validation.appendChild(doc.createTextNode(String.valueOf(gsModules.getModuleInsctiption(mod.getId(),et.getId()).getValidation())));
		etudiant.appendChild(validation);
		}
		} catch (DataBaseException e) {
			LOG.error("Erreur lors d'une operation sur BD" + e);
		}
					
}
/**
 * Transformation à un fichier XML
 * @param doc
 * @param chemin
 * @param name
 * @throws TransformerException
 */
	private static void transformer(Document doc,String chemin,String name) throws TransformerException{
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(chemin+"\\ListeNotes_"+name+".xml"));
 
		transformer.transform(source, result);

		
	}
	
	/**
	 * Ajouter les notes d'une matière
	 * @param doc
	 * @param etudiant
	 * @param mat
	 * @param et
	 * @throws DataBaseException 
	 */
	
	private static void matiereNoteTag(Document doc,Element etudiant ,Matiere mat,Etudiant et) throws DataBaseException{
		
		try {
		Element matiere = doc.createElement("matiere");
		etudiant.appendChild(matiere);
						
		Attr attr = doc.createAttribute("id");
		attr.setValue(mat.getId().toString());
		matiere.setAttributeNode(attr);
				
		Note note = gsNotes.getNote(mat.getId().intValue(),et.getId().intValue(),Configuration.getInstance().getPropertie().getAnneeuniv());
		
						
		Element noteSn = doc.createElement("noteSN");
		noteSn.appendChild(doc.createTextNode(String.valueOf(note.getNoteSN())));
		matiere.appendChild(noteSn);
	
		Element noteSr = doc.createElement("noteSR");
		noteSr.appendChild(doc.createTextNode(String.valueOf(note.getNoteSR())));
		matiere.appendChild(noteSr);
		}
		catch (DataBaseException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
		} catch (ElementNotFoundException e) {
			new JOptionPane().showMessageDialog(null, "Le fichier ne peut pas être généré","Message!!", JOptionPane.ERROR_MESSAGE);
			LOG.error("Erreur lors d'une opération sur la base de données" + e);
			throw new DataBaseException("Erreur lors d'une opération sur la base de données", e);
		}
	}
	
	private static String choixChemin(){
		
		String chemin="";
		
		// permet de choisir l'emplaçement de stockage du fichier 
        
		final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
     
             int val_retour = fc.showSaveDialog(null);

             if (val_retour == JFileChooser.APPROVE_OPTION) {
                File fichier = fc.getSelectedFile();
              
                chemin=fichier.getAbsolutePath();
                
                // 
                JOptionPane jop = new JOptionPane();
        		jop.showMessageDialog(null, "Le fichier a été généré avec succès","Message!!", JOptionPane.INFORMATION_MESSAGE);
        	
              
             } 
       return chemin;      
	}
	
}