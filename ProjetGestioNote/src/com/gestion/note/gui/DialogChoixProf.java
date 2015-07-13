package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionClasses;
import com.gestion.note.bll.GestionModules;
import com.gestion.note.bll.GestionProf;
import com.gestion.note.bo.Classe;
import com.gestion.note.bo.Etudiant;
import com.gestion.note.bo.Module;
import com.gestion.note.config.ConfigurationLoader;
import com.gestion.note.db.DataBaseException;

/**
 * cette classe permet de configurer le professeur propriètaire
 * de l'application
 * @author BOULUAD
 *
 */
public class DialogChoixProf extends JDialog implements  ActionListener{

	private LabledComboBox comboListProf;
	private JButton validerButton = new JButton("Valdier");
	private JButton annulerButton = new JButton("Annuler");
	
	public DialogChoixProf() throws DataBaseException {

		
		JPanel panelGlobal =new JPanel();
    	BorderLayout borderGlobal=new BorderLayout();
		JPanel panel3=new JPanel();
		BorderLayout border3=new BorderLayout();
		
		panel3.add(validerButton,border3.WEST);
		panel3.add(annulerButton,border3.EAST);
		// Initialisation des listes

		GestionProf lGsProf = GestionProf.getInstance();
		
		List<String> listProf;
		try {
			listProf = lGsProf.getProf();
			String[] tabChoix = new String[listProf.size()];
			for (int i = 0; i < listProf.size(); i++) {

				tabChoix[i] = listProf.get(i);

			}
			comboListProf = new LabledComboBox(tabChoix, "Choisir votre Nom : ");
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		annulerButton.addActionListener(this);
		validerButton.addActionListener(this);

		
		JPanel panel2=new JPanel();
		panel2.setLayout(new GridLayout(2,1)); 
		panel2.add(comboListProf);
		
		panelGlobal.add(panel2,borderGlobal.CENTER);
        panelGlobal.add(panel3,borderGlobal.SOUTH);
		add(panelGlobal);
		
		setModal(true);
		setTitle("Configuration : ");
		setResizable(false);
		setSize(450,140);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == annulerButton){
			setVisible(false);
		}
		if (e.getSource() == validerButton) {

			String selected = (String) comboListProf.getCombo().getSelectedItem();
			String[] parts = selected.split(" ");

			try {
				
				 GestionProf.getInstance().updateStatProf(parts[0],parts[1]);
					
				setVisible(false);
			} catch (DataBaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
