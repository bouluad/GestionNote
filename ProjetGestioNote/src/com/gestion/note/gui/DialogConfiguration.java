package com.gestion.note.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.gestion.note.bll.ElementNotFoundException;
import com.gestion.note.bll.GestionProf;
import com.gestion.note.bo.parametre;
import com.gestion.note.config.Configuration;
import com.gestion.note.db.DataBaseException;

/**
 * cette classe permet de configurer le professeur propriètaire
 * de l'application 
 * 
 *
 */
public class DialogConfiguration extends JDialog implements  ActionListener{
	
	private final Logger LOG = Logger.getLogger(getClass());

	private LabledComboBox comboListProf;
	private LabledTextField annee;
	private LabledTextField semistre;
	private LabledTextField seuilCp;
	private LabledTextField seuilCi;
	private JButton validerButton = new JButton("Valdier");
	private JButton annulerButton = new JButton("Annuler");
	
	public DialogConfiguration() throws DataBaseException, ElementNotFoundException {

		
		JPanel panelGlobal =new JPanel();
    	
		JPanel panel3=new JPanel();
		BorderLayout border3=new BorderLayout();
		
		panel3.add(validerButton,border3.WEST);
		panel3.add(annulerButton,border3.EAST);
		

		GestionProf lGsProf = GestionProf.getInstance();
		Configuration config = Configuration.getInstance();
		
		parametre p=new parametre();
		
		p=config.getPropertie();
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

		   annee=new LabledTextField(8,"Année d'étude");
		semistre=new LabledTextField(8,"Semestre     ");
		 seuilCp=new LabledTextField(8,"Seuil CP     ");
		 seuilCi=new LabledTextField(8,"Seuil CI     ");
		
		annee.setTxt(p.getAnneeuniv());
		semistre.setTxt(p.getSemestre());
		seuilCp.setTxt(p.getSeuilCp());
		seuilCi.setTxt(p.getSeuilCi());
		
		JPanel panel2=new JPanel();
		panel2.setBorder(BorderFactory.createTitledBorder("Professeur :"));
		panel2.setLayout(new GridLayout(1,1)); 
		panel2.add(comboListProf);
		
		JPanel panelProp=new JPanel();
		panelProp.setLayout(new GridLayout(4,1));
		panelProp.setBorder(BorderFactory.createTitledBorder("Proprites :"));
		
		panelProp.add(annee);
		panelProp.add(semistre);
		panelProp.add(seuilCp);
		panelProp.add(seuilCi);
		
		panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
		
		panelGlobal.add(panel2);
		panelGlobal.add(panelProp);
		
        panelGlobal.add(panel3);
		add(panelGlobal,new BorderLayout().CENTER);
		
		setModal(true);
		setTitle("Configuration : ");
		setResizable(false);
		//pack();
		setSize(450,300);
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
				 Configuration.getInstance().update(Integer.parseInt(annee.getTxt()),Integer.parseInt(semistre.getTxt()),
						 Integer.parseInt(seuilCp.getTxt()),Integer.parseInt(seuilCi.getTxt()));
				 
				setVisible(false);
			} catch (DataBaseException e1) {
				LOG.error("Error while trying to connect to database",e1);
			} catch (SQLException e1) {
				LOG.error("Erreur lors d'une opération sur la base de données :" , e1);
			
			}

		}

	}

}
