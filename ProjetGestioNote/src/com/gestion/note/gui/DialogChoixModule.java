package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionClasses;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bo.Classe;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Matiere;
import com.gestion.note.bo.Module;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;

public class DialogChoixModule  extends JDialog implements ItemListener, ActionListener{

	private LabledComboBox comboListClasses;
	private LabledComboBox comboListModules;
	
	private JButton validerButton = new JButton("Valdier");
	private JButton annulerButton = new JButton("Annuler");
	private List<Etudiant> listStudents = new ArrayList<Etudiant>();
	private MainFrame frame=new MainFrame();
	
	
	public DialogChoixModule() throws DataBaseException{
		
		setModal(true);
		setTitle("La consultation Des Notes  ");
		setResizable(false);
		setLocationRelativeTo(frame);
		setSize(450,250);
		
			
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
		
		
		
		try {
			listClasses = lGsClasses.getClasses();
		} catch (ElementNotFoundException e) {
			
		}
		try {
			listModules = lGModules.getModules();
		} catch (ElementNotFoundException e) {
			
		}
		

		
		String[] tabClassesChoix = new String[listClasses.size()];
		String[] tabModulesChoix = new String[listModules.size()];
		
		
		
		for (int i = 0; i < listClasses.size(); i++) {

			tabClassesChoix[i] = listClasses.get(i).getNom();

		}

		for (int i = 0; i < listModules.size(); i++) {

			tabModulesChoix[i] = listModules.get(i).getIntitule();

		}
		
		
		
		comboListClasses = new LabledComboBox(tabClassesChoix, "Choisir la classe");
		comboListModules = new LabledComboBox(tabModulesChoix, "Choisir le module");
		
		
		
		
		
		comboListClasses.addItemListener(this);
		comboListModules.addItemListener(this);
		annulerButton.addActionListener(this);
		
		validerButton.addActionListener(this);
		
		
		JPanel panel2=new JPanel();
		panel2.setLayout(new GridLayout(2,1)); 
		panel2.add(comboListClasses);
		panel2.add(comboListModules);
		panelGlobal.add(panel2,borderGlobal.CENTER);
        panelGlobal.add(panel3,borderGlobal.SOUTH);
		add(panelGlobal);
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == validerButton) {

			String selected = (String) comboListClasses.getCombo().getSelectedItem();

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

	public List<Etudiant> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Etudiant> listStudents) {
		this.listStudents = listStudents;
	}
	
	
	

}
