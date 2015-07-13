package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ldap.Rdn;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionClasses;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bo.Classe;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;

/**
 *  choisir une classe,module et une matière 
 * à l'aide d'une boite de dialogue
 * @author BOULUAD
 *
 */
public class DialogChoixMatiere extends JDialog implements ItemListener, ActionListener {

	private LabledComboBox comboListClasses;
	private LabledComboBox annee;
	private LabledComboBox comboListModules;
	private LabledComboBox comboListMatieres;
	private JButton validerButton = new JButton("Valdier");
	private JButton annulerButton = new JButton("Annuler");
	private List<Etudiant> listStudents = new ArrayList<Etudiant>();
	private JRadioButton sessionNormal=new JRadioButton(" normale",true);
	private JRadioButton sessionRatt=new JRadioButton(" rattrapage");
	private ButtonGroup ChoixSession =new ButtonGroup();
	//attribut pour avoir la session selectionné
	private int Radiostat;
	
	/**
	 * Constructeur de la boite de dialogue
	 */
	
	public DialogChoixMatiere() throws DataBaseException {


		setModal(true);
		setTitle("Saisir la note ");
		setResizable(false);
		setSize(450,240);
		setLocationRelativeTo(null);
	    
		
		ChoixSession.add(sessionNormal);
		ChoixSession.add(sessionRatt);
		 
		JPanel panelGlobal =new JPanel();
    	BorderLayout borderGlobal=new BorderLayout();
		JPanel panel3=new JPanel();
		BorderLayout border3=new BorderLayout();
		
		panel3.add(validerButton,border3.WEST);
	
		panel3.add(annulerButton,border3.EAST);
		
		GestionClasses lGsClasses = GestionClasses.getInstance();
		GestionModules lGModules = GestionModules.getInstance();

		List<Classe> listClasses = null;
		List<Module> listModules = null;
		List<Matiere> listMatieres = null;
		
		try {
			listClasses = lGsClasses.getClasses();
		} catch (ElementNotFoundException e) {
			// Exception volentairement ignorée
		}
		
		
		
		String[] tabClassesChoix = new String[listClasses.size()];		

		for (int i = 0; i < listClasses.size(); i++) {

			tabClassesChoix[i] = listClasses.get(i).getNom();

		}

		try {
			listModules = lGModules.getClasseModules(listClasses.get(0).getId());
		} catch (ElementNotFoundException e) {
			// Exception volentairement ignorée
		}
		
		String[] tabModulesChoix = new String[listModules.size()];		
		for (int i = 0; i < listModules.size(); i++) {

			tabModulesChoix[i] = listModules.get(i).getIntitule();

		}

		try {
			listMatieres = lGModules.getMatieresModule(listModules.get(0).getId());
		} catch (ElementNotFoundException e) {
			// Exception volentairement ignorée
		}
		
		String[] tabMatiereChoix = new String[listModules.size()];		
		for (int i = 0; i < listMatieres.size(); i++) {

			tabMatiereChoix[i] = listMatieres.get(i).getIntitule();

		}
		
		comboListClasses = new LabledComboBox(tabClassesChoix, "Choisir la classe");
		comboListModules = new LabledComboBox(tabModulesChoix, "Choisir le module");
		comboListMatieres = new LabledComboBox(tabMatiereChoix, "Choisir la matière");

		comboListClasses.addItemListener(this);
		comboListModules.addItemListener(this);
		comboListMatieres.addItemListener(this);
		annulerButton.addActionListener(this);
		validerButton.addActionListener(this);
		
		sessionNormal.addActionListener(this);
		sessionRatt.addActionListener(this);

		
		JPanel pnlChoixSess=new JPanel();
		JLabel lblChoix=new JLabel("Session ");
		pnlChoixSess.add(lblChoix);
		pnlChoixSess.add(sessionNormal);
		pnlChoixSess.add(sessionRatt);

		JPanel panel2=new JPanel();
		panel2.setLayout(new GridLayout(4,1)); 
		panel2.add(comboListClasses);
		panel2.add(comboListModules);
		panel2.add(comboListMatieres);
		panel2.add(pnlChoixSess);
	
		panelGlobal.add(panel2,borderGlobal.CENTER);
        panelGlobal.add(panel3,borderGlobal.SOUTH);
		add(panelGlobal);
		
		

	}

	public void actionPerformed(ActionEvent e) {

		
		if (e.getSource() == validerButton) {
			String selected = (String) comboListModules.getCombo().getSelectedItem();

			try {
				listStudents = GestionClasses.getInstance().getClasseStudents(selected,
						Integer.parseInt(ConfigurationLoader.MAPCONFIG.get(ConfigurationLoader.ANNEE_UNIV)));
				setVisible(false);
			} catch (DataBaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ElementNotFoundException e1) {
				// Exception volentairement ignorée
			}

		}
		
		if(e.getSource() == annulerButton){
			setVisible(false);
		}
		if(e.getSource() == sessionNormal){
			
			Radiostat=0;
			
		}
	if(e.getSource() == sessionRatt){
			
			Radiostat=1;
			
		}
		

	}
	
	
	

	public void itemStateChanged(ItemEvent e) {
		GestionModules lGsModules = null;
		GestionClasses lGsClasses=null;
		lGsModules = GestionModules.getInstance();
		lGsClasses=GestionClasses.getInstance();
		Object source = e.getSource();
		if (source == comboListClasses.getCombo()) {

			try {

				String nomClasse =(String) comboListClasses.getCombo().getSelectedItem();
				Long idClass = lGsClasses.findid(nomClasse);
				List<Module> list = lGsModules.getClasseModules(idClass);
				
				String[] modules = new String[list.size()];

				for (int i = 0; i < list.size(); i++) {
					System.out.println(i);
					modules[i] = list.get(i).getIntitule();
				}
				comboListModules.refresh(modules);

			} catch (DataBaseException ex) {
				// TODO : Traiter cette exception
				ex.printStackTrace();
			} catch (ElementNotFoundException e1) {
				// Exception volentairement ignorée
			}

		} else if (source == comboListModules.getCombo()) {

			try {
				
				String nommod =(String) comboListModules.getCombo().getSelectedItem();
				Long idmod = lGsClasses.findid(nommod);
				List<Matiere> listMa = lGsModules.getMatieresModule(idmod);
				String[] matieres = new String[listMa.size()];

				for (int i = 0; i < listMa.size(); i++) {
					System.out.println(i);
					matieres[i] = listMa.get(i).getIntitule();
				}
				comboListMatieres.refresh(matieres);
			} catch (DataBaseException ex) {
				// TODO : Traiter cette exception
				ex.printStackTrace();

			} catch (ElementNotFoundException e1) {
				// Exception volentairement ignorée
			}

		} else if (source == comboListMatieres.getCombo()) {

		}

	}

	public LabledComboBox getComboListClasses() {
		return comboListClasses;
	}

	public void setComboListClasses(LabledComboBox comboListClasses) {
		this.comboListClasses = comboListClasses;
	}

	public LabledComboBox getComboListModules() {
		return comboListModules;
	}

	public void setComboListModules(LabledComboBox comboListModules) {
		this.comboListModules = comboListModules;
	}

	public LabledComboBox getComboListMatieres() {
		return comboListMatieres;
	}

	public void setComboListMatieres(LabledComboBox comboListMatieres) {
		this.comboListMatieres = comboListMatieres;
	}

	public List<Etudiant> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Etudiant> listStudents) {
		this.listStudents = listStudents;
	}


	public static void main(String[] args) {
		try {
			
			
			new DialogChoixMatiere();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRadiostat() {
		return Radiostat;
	}

	public void setRadiostat(int radiostat) {
		Radiostat = radiostat;
	}
	
	
}
