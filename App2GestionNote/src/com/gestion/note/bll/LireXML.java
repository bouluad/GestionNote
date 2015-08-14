package com.gestion.note.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.EtudiantNote;
import com.gestion.note.bo.Note;

public class LireXML {
	
	private List<EtudiantNote> etudiantNotes=new ArrayList<EtudiantNote>();
	private Etudiant etudiant=null;
	private Note note=null ;
	private EtudiantNote etudiantNote=null;

	private String titre;
	
	public LireXML(String chemin){
    try {
    	 

    File file = new File(chemin);
 
	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                             .newDocumentBuilder();
 
	Document doc = dBuilder.parse(file);
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
 
	if (doc.hasChildNodes()) {
 
		printNote(doc.getChildNodes());
 
	}
 
    } catch (Exception e) {
    	System.out.println(e.getMessage());
    }
}
    private void printNote(NodeList nodeList) {
    	
        for (int count = 0; count < nodeList.getLength(); count++) {

       
    	Node tempNode = nodeList.item(count);
    	 
    	// make sure it's element node.
    	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
    		
    		if ("etudiant".equals(tempNode.getNodeName())) {  
    	        etudiant=new Etudiant();
    	        note =new Note();
			}
    		if ("id".equals(tempNode.getNodeName())) {
				etudiant.setId(Long.valueOf(tempNode.getTextContent()));
			}
    		if ("cne".equals(tempNode.getNodeName())) {
				etudiant.setCne(tempNode.getTextContent());
			}
    		if ("nom".equals(tempNode.getNodeName()) && etudiant !=null) {
				etudiant.setNom(tempNode.getTextContent());
    			
			}
    		if ("prenom".equals(tempNode.getNodeName())) {
				etudiant.setPrenom(tempNode.getTextContent());

			}
    		if ("noteSN".equals(tempNode.getNodeName())) {
				note.setNoteSN(Double.parseDouble(tempNode.getTextContent()));

			}
    		if ("noteSR".equals(tempNode.getNodeName())) {
				note.setNoteSR(Double.parseDouble(tempNode.getTextContent()));
				
        	   	etudiantNote=new EtudiantNote(etudiant, note);
             	etudiantNotes.add(etudiantNote);
    		}
		
     
    		// get node name and value
    		System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
    		System.out.println("Node Value =" + tempNode.getTextContent());
     
    		if (tempNode.hasAttributes()) {
     
    			// get attributes names and values
    			NamedNodeMap nodeMap = tempNode.getAttributes();
     
    			for (int i = 0; i < nodeMap.getLength(); i++) {
     
    				Node node = nodeMap.item(i);
    				if("titre".equals(node.getNodeName())){
    					setTitre(node.getNodeValue());
    				}
    				System.out.println("attr name : " + node.getNodeName());
    				System.out.println("attr value : " + node.getNodeValue());
     
    			}
     
    		}
     
    		if (tempNode.hasChildNodes()) {
     
    			// loop again if has child nodes
    			printNote(tempNode.getChildNodes());
     
    		}
      
    		System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
    	}
     
        }
     
      }
	public List<EtudiantNote> getEtudiantNotes() {
		return etudiantNotes;
	}
	public void setEtudiantNotes(List<EtudiantNote> etudiantNotes) {
		this.etudiantNotes = etudiantNotes;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}


    
}
