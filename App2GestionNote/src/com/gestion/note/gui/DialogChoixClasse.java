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
import com.gestion.note.bo.Classe;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.config.Configuration;
import com.gestion.note.db.DataBaseException;

/**
 * cette classe permet de choisir une classe et un module
 * à l'aide d'une boite de dialogue
 *
 *
 */
public class DialogChoixClasse extends JDialog implements ItemListener, ActionListener
{

	
	private LabledComboBox comboListClasses;
	private JButton validerButton = new JButton("Valdier");
	private JButton annulerButton = new JButton("Annuler");
	private Long idClasseChoisi;
	private List<Etudiant> listStudents = new ArrayList<Etudiant>();

	

	/**
	 * Constructeur de la boite de dialogue
	 * 
	 * @throws DataBaseException
	 */
	public DialogChoixClasse() throws DataBaseException {

		setModal(true);
		setTitle("La consultation des notes  ");
		setResizable(false);
		setSize(450,140);
		setLocationRelativeTo(null);
		
		
		JPanel panelGlobal =new JPanel();
    	BorderLayout borderGlobal=new BorderLayout();
		JPanel panel3=new JPanel();
		BorderLayout border3=new BorderLayout();
		
		panel3.add(validerButton,border3.WEST);
		panel3.add(annulerButton,border3.EAST);
		// Initialisation des listes

		GestionClasses lGsClasses = GestionClasses.getInstance();

		List<Classe> listClasses = null;
		try {
			listClasses = lGsClasses.getClasses();
		} catch (ElementNotFoundException e) {
			
		}
		
		String[] tabClassesChoix = new String[listClasses.size()];

		for (int i = 0; i < listClasses.size(); i++) {

			tabClassesChoix[i] = listClasses.get(i).getNom();
		}

		comboListClasses = new LabledComboBox(tabClassesChoix, "Choisir la classe");

		comboListClasses.addItemListener(this);
		annulerButton.addActionListener(this);
		validerButton.addActionListener(this);

		
		JPanel panel2=new JPanel();
		panel2.setLayout(new GridLayout(1,1)); 
		panel2.add(comboListClasses);
		panelGlobal.add(panel2,borderGlobal.CENTER);
        panelGlobal.add(panel3,borderGlobal.SOUTH);
		add(panelGlobal);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == annulerButton){
			setVisible(false);
		}
		if (e.getSource() == validerButton) {

			String selected = (String) comboListClasses.getCombo().getSelectedItem();

			try {
				listStudents = GestionClasses.getInstance().getClasseStudents(GestionClasses.getInstance().findid(selected));
			} catch (DataBaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ElementNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			try {
				idClasseChoisi = GestionClasses.getInstance().findid(selected);
			
				setVisible(false);
			} catch (DataBaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ElementNotFoundException e1) {
				// Exception volentairement ignorée
			}

		}

	}

	public void itemStateChanged(ItemEvent e) {
	
	}


	public LabledComboBox getComboListClasses() {
		return comboListClasses;
	}

	public void setComboListClasses(LabledComboBox comboListClasses) {
		this.comboListClasses = comboListClasses;
	}

	public JButton getValiderButton() {
		return validerButton;
	}

	public void setValiderButton(JButton validerButton) {
		this.validerButton = validerButton;
	}

	public JButton getAnnulerButton() {
		return annulerButton;
	}

	public void setAnnulerButton(JButton annulerButton) {
		this.annulerButton = annulerButton;
	}

	public Long getIdClasseChoisi() {
		return idClasseChoisi;
	}

	public void setIdClasseChoisi(Long idClasseChoisi) {
		this.idClasseChoisi = idClasseChoisi;
	}

	public List<Etudiant> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Etudiant> listStudents) {
		this.listStudents = listStudents;
	}



}
